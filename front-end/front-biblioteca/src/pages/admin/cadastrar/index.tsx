import { useState } from "react";
import { useNavigate } from "react-router-dom";
// O cabeçalho para Admin geralmente é 'admin/header' ou algo assim,
// mas vou manter a importação original do seu código:
import Header from "../../../components/admin/header";
import "./style.css";

function CadastrarLivroAdmin() {
    const navigate = useNavigate();

    const [isSaving, setIsSaving] = useState<boolean>(false);
    const [message, setMessage] = useState<{ type: "success" | "error"; text: string } | null>(null);

    const [formState, setFormState] = useState({
        isbn: "",
        numeroChamada: "",
        exemplares: 1,
        lingua: "",
        autores: [] as string[],
        titulo: "",
        edicao: "",
        localPublicacao: "",
        editora: "",
        anoPublicacao: 0, 
        descricaoFisica: "",
        tituloSerie: "",
        assuntos: [] as string[],
        cutter: "",
        cdd: "",
    });

    // INPUT COMUM
    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
        const { name, value, type } = e.target;
        setFormState(prev => ({
            ...prev,
            [name]: type === "number" ? Number(value) : value,
        }));
    };

    // REGISTRAR LIVRO (POST)
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsSaving(true);
        setMessage(null);

        const payload = {
            ...formState,
            autores: formState.autores.filter(a => a.trim() !== ""),
            assuntos: formState.assuntos.filter(a => a.trim() !== ""),
        };

        try {
            const response = await fetch("http://localhost:8080/livro", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload),
            });

            if (!response.ok) throw new Error();

            setMessage({ type: "success", text: "Livro cadastrado com sucesso!" });
            setTimeout(() => navigate("/home/admin"), 1500);

        } catch {
            setMessage({ type: "error", text: "Erro ao cadastrar o livro." });
        } finally {
            setIsSaving(false);
        }
    };

    return (
        // 🔄 Passo 1: Envolver tudo em admin-container
        <div className="admin-container">
            <Header /> {/* 🔄 Passo 2: Renderizar o Header aqui */}

            {/* 🔄 Passo 3: Conteúdo principal dentro de admin-content */}
            <main className="admin-content">
                <h1 className="admin-title">Administração de Acervo</h1>
                <p className="admin-subtitle">
                    Use este formulário para registrar novos livros no sistema.
                </p>

                {/* O conteúdo do card existente */}
                <div className="card">
                    <div className="card-header">
                        <h2 className="card-header-title">Cadastrar Livro</h2>

                        <button className="btn-voltar" onClick={() => navigate("/home/admin")}>
                            Voltar
                        </button>
                    </div>

                    {message && <div className={`message ${message.type}`}>{message.text}</div>}

                    <form onSubmit={handleSubmit} className="form-grid">

                        {/* ... O restante do formulário é o mesmo ... */}
                        
                        {/* CAMPOS SIMPLES */}
                        <div className="form-group">
                            <label>ISBN *</label>
                            <input name="isbn" value={formState.isbn} onChange={handleChange} required />
                        </div>

                        <div className="form-group">
                            <label>Título *</label>
                            <input name="titulo" value={formState.titulo} onChange={handleChange} required />
                        </div>

                        <div className="form-group">
                            <label>Título da Série</label>
                            <input name="tituloSerie" value={formState.tituloSerie} onChange={handleChange} />
                        </div>

                        <div className="form-group">
                            <label>Edição</label>
                            <input name="edicao" value={formState.edicao} onChange={handleChange} />
                        </div>

                        {/* AUTORES */}
                        <div className="form-group full">
                            <label>Autores *</label>

                            {formState.autores.map((autor, index) => (
                                <div key={`autor-${index}`} className="tag-item">
                                    <input
                                        value={autor}
                                        onChange={(e) => {
                                            const newList = [...formState.autores];
                                            newList[index] = e.target.value;
                                            setFormState(prev => ({ ...prev, autores: newList }));
                                        }}
                                    />
                                    <button
                                        type="button"
                                        className="btn-remove"
                                        onClick={() =>
                                            setFormState(prev => ({
                                                ...prev,
                                                autores: prev.autores.filter((_, i) => i !== index),
                                            }))
                                        }
                                    >
                                        X
                                    </button>
                                </div>
                            ))}

                            <button
                                type="button"
                                className="btn-add"
                                onClick={() =>
                                    setFormState(prev => ({
                                        ...prev,
                                        autores: [...prev.autores, ""],
                                    }))
                                }
                            >
                                + Adicionar Autor
                            </button>
                        </div>

                        {/* ASSUNTOS */}
                        <div className="form-group full">
                            <label>Assuntos *</label>

                            {formState.assuntos.map((assunto, index) => (
                                <div key={`assunto-${index}`} className="tag-item">
                                    <input
                                        value={assunto}
                                        onChange={(e) => {
                                            const newList = [...formState.assuntos];
                                            newList[index] = e.target.value;
                                            setFormState(prev => ({ ...prev, assuntos: newList }));
                                        }}
                                    />

                                    <button
                                        type="button"
                                        className="btn-remove"
                                        onClick={() =>
                                            setFormState(prev => ({
                                                ...prev,
                                                assuntos: prev.assuntos.filter((_, i) => i !== index),
                                            }))
                                        }
                                    >
                                        X
                                    </button>
                                </div>
                            ))}

                            <button
                                type="button"
                                className="btn-add"
                                onClick={() =>
                                    setFormState(prev => ({
                                        ...prev,
                                        assuntos: [...prev.assuntos, ""],
                                    }))
                                }
                            >
                                + Adicionar Assunto
                            </button>
                        </div>

                        {/* CAMPOS RESTANTES */}
                        <div className="form-group">
                            <label>Número de Chamada</label>
                            <input name="numeroChamada" value={formState.numeroChamada} onChange={handleChange} />
                        </div>

                        <div className="form-group">
                            <label>Cutter</label>
                            <input name="cutter" value={formState.cutter} onChange={handleChange} />
                        </div>

                        <div className="form-group">
                            <label>CDD</label>
                            <input name="cdd" value={formState.cdd} onChange={handleChange} />
                        </div>

                        <div className="form-group">
                            <label>Editora</label>
                            <input name="editora" value={formState.editora} onChange={handleChange} />
                        </div>

                        <div className="form-group">
                            <label>Língua</label>
                            <input name="lingua" value={formState.lingua} onChange={handleChange} />
                        </div>

                        <div className="form-group">
                            <label>Local de Publicação</label>
                            <input name="localPublicacao" value={formState.localPublicacao} onChange={handleChange} />
                        </div>

                        <div className="form-group">
                            <label>Ano de Publicação</label>
                            <input
                                name="anoPublicacao" 
                                value={formState.anoPublicacao}
                                onChange={handleChange}
                            />
                        </div>

                        <div className="form-group full">
                            <label>Descrição Física</label>
                            <input name="descricaoFisica" value={formState.descricaoFisica} onChange={handleChange} />
                        </div>

                        <div className="form-group">
                            <label>Exemplares *</label>
                            <input
                                type="number"
                                min={1}
                                name="exemplares"
                                value={formState.exemplares}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        {/* BOTÃO */}
                        <div className="form-actions full">
                            <button type="submit" className="btn-primary" disabled={isSaving}>
                                {isSaving ? "Cadastrando..." : "Cadastrar Livro"}
                            </button>
                        </div>

                    </form>
                </div>
            </main>
        </div>
    );
}

export default CadastrarLivroAdmin;