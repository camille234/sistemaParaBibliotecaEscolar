import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./style.css";

interface Emprestimo {
    id: number;
    matriculaAluno: string;
    isbnLivro: string;
    dataRetirada: string;
    dataDevolucaoPrevista: string;
    status: string;
}

function RegistrarDevolucaoAdmin() {
    const navigate = useNavigate();
    const [emprestimos, setEmprestimos] = useState<Emprestimo[]>([]);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState<{ type: "success" | "error"; text: string } | null>(null);

    // Buscar empréstimos pendentes e atrasados
    useEffect(() => {
        const fetchEmprestimos = async () => {
            try {
                const response = await fetch("http://localhost:8080/emprestimos/pendentes-atrasados");

                if (!response.ok) throw new Error();

                const data = await response.json();
                setEmprestimos(data);
            } catch {
                setMessage({ type: "error", text: "Erro ao carregar empréstimos." });
            } finally {
                setLoading(false);
            }
        };

        fetchEmprestimos();
    }, []);

    // Registrar devolução
    const registrarDevolucao = async (matricula: string) => {
        try {
            const response = await fetch(`http://localhost:8080/emprestimos/devolver/${matricula}`, {
                method: "PUT",
            });

            if (!response.ok) throw new Error();

            setMessage({ type: "success", text: "Devolução registrada com sucesso!" });

            // Atualizar lista removendo item devolvido
            setEmprestimos(prev => prev.filter(e => e.matriculaAluno !== matricula));

        } catch {
            setMessage({ type: "error", text: "Erro ao registrar devolução." });
        }
    };

    return (
        <div className="page-container">
            <div className="card">
                <div className="card-header">
                    <h2 className="card-header-title">Registar Devolução</h2>

                    <button className="btn-voltar" onClick={() => navigate("/home/admin")}>
                        Voltar
                    </button>
                </div>

                {message && (
                    <div className={`message ${message.type}`}>
                        {message.text}
                    </div>
                )}

                {loading ? (
                    <p style={{ textAlign: "center", marginTop: "20px" }}>Carregando...</p>
                ) : (
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Livro</th>
                                <th>Aluno</th>
                                <th>Matrícula</th>
                                <th>Data Empréstimo</th>
                                <th>Ação</th>
                            </tr>
                        </thead>

                        <tbody>
                            {emprestimos.length === 0 ? (
                                <tr>
                                    <td colSpan={5} style={{ textAlign: "center", padding: "15px" }}>
                                        Nenhum empréstimo pendente ou atrasado.
                                    </td>
                                </tr>
                            ) : (
                                emprestimos.map(e => (
                                    <tr key={e.id}>
                                        <td>{e.isbnLivro}</td>
                                        <td>{/* Você pode trocar pelo nome do aluno se tiver consulta */}—</td>
                                        <td>{e.matriculaAluno}</td>
                                        <td>{new Date(e.dataRetirada).toLocaleDateString("pt-BR")}</td>

                                        <td>
                                            <button
                                                className="btn-primary"
                                                onClick={() => registrarDevolucao(e.matriculaAluno)}
                                            >
                                                Registrar Devolução
                                            </button>
                                        </td>
                                    </tr>
                                ))
                            )}
                        </tbody>
                    </table>
                )}
            </div>
        </div>
    );
}

export default RegistrarDevolucaoAdmin;
