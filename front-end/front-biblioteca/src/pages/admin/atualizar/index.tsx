import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./style.css";

interface Livro {
    id: number;
    titulo: string;
    tituloSerie: string;
    numeroRegistro: string;
    autorPessoal: string;
    segundoAutor: string;
    isbn: string;
    numeroChamada: string;
    edicao: string;
    linguas: string;
    areaDescricaoFisica: string;
    areaPublicacao: string;
    assuntoTopico: string;
    categoria: string;
    quantidade: number;
}

const categories = [
    "Literatura Estrangeira",
    "Ficção Científica",
    "Romance",
    "História",
    "Técnico/Científico",
];

function EditarLivroAdmin() {
    const navigate = useNavigate();
    const { id } = useParams<{ id: string }>();

    const [livro, setLivro] = useState<Livro | null>(null);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [isSaving, setIsSaving] = useState<boolean>(false);
    const [message, setMessage] = useState<{ type: "success" | "error"; text: string } | null>(null);

    const [formState, setFormState] = useState({
        titulo: "",
        tituloSerie: "",
        numeroRegistro: "",
        autorPessoal: "",
        segundoAutor: "",
        isbn: "",
        numeroChamada: "",
        edicao: "",
        linguas: "",
        areaDescricaoFisica: "",
        areaPublicacao: "",
        assuntoTopico: "",
        categoria: categories[0],
        quantidade: 0,
    });

    useEffect(() => {
        if (!id) {
            setIsLoading(false);
            setMessage({ type: "error", text: "ID do livro não fornecido." });
            return;
        }

        const fetchLivro = async () => {
            await new Promise((resolve) => setTimeout(resolve, 500));

            const simulatedData: Livro = {
                id: Number(id),
                titulo: "A Dama do Lago",
                tituloSerie: "The Witcher",
                numeroRegistro: "REG-2023-001",
                autorPessoal: "Andrzej Sapkowski",
                segundoAutor: "Tradução: E. Almeida",
                isbn: "978-8542211912",
                numeroChamada: "FIC/S-A 101",
                edicao: "1ª Edição",
                linguas: "Português",
                areaDescricaoFisica: "608 p., 23 cm",
                areaPublicacao: "São Paulo, Brasil",
                assuntoTopico: "Fantasia Medieval",
                categoria: "Literatura Estrangeira",
                quantidade: 5,
            };

            try {
                setLivro(simulatedData);

                setFormState({
                    titulo: simulatedData.titulo,
                    tituloSerie: simulatedData.tituloSerie,
                    numeroRegistro: simulatedData.numeroRegistro,
                    autorPessoal: simulatedData.autorPessoal,
                    segundoAutor: simulatedData.segundoAutor,
                    isbn: simulatedData.isbn,
                    numeroChamada: simulatedData.numeroChamada,
                    edicao: simulatedData.edicao,
                    linguas: simulatedData.linguas,
                    areaDescricaoFisica: simulatedData.areaDescricaoFisica,
                    areaPublicacao: simulatedData.areaPublicacao,
                    assuntoTopico: simulatedData.assuntoTopico,
                    categoria: simulatedData.categoria,
                    quantidade: simulatedData.quantidade,
                });

            } catch (error) {
                setMessage({ type: "error", text: "Erro ao carregar os dados." });
            } finally {
                setIsLoading(false);
            }
        };

        fetchLivro();
    }, [id]);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value, type } = e.target;
        setFormState((prev) => ({
            ...prev,
            [name]: type === "number" ? Number(value) || 0 : value,
        }));
    };

    const handleUpdate = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsSaving(true);
        setMessage(null);

        if (!formState.titulo || !formState.isbn || formState.quantidade <= 0) {
            setMessage({
                type: "error",
                text: "Preencha os campos obrigatórios.",
            });
            setIsSaving(false);
            return;
        }

        await new Promise((resolve) => setTimeout(resolve, 1000));

        setMessage({ type: "success", text: "Livro atualizado com sucesso!" });
        setLivro({ ...livro!, ...formState });

        setIsSaving(false);
    };

    const handleDelete = async () => {
        if (!window.confirm("Tem certeza que deseja deletar?")) return;

        setIsSaving(true);
        setMessage(null);

        await new Promise((resolve) => setTimeout(resolve, 1000));

        setMessage({ type: "success", text: "Livro deletado. Redirecionando..." });

        setTimeout(() => navigate("/consulta/admin"), 2000);
    };

    if (isLoading) {
        return (
            <div className="page-container">
                <p style={{ textAlign: "center", marginTop: "40px" }}>Carregando...</p>
            </div>
        );
    }

    if (!livro) {
        return (
            <div className="page-container">
                <p style={{ textAlign: "center", marginTop: "40px" }}>
                    {message?.text || "Erro ao carregar"}
                </p>
            </div>
        );
    }

    return (
        <div className="page-container">
            <div className="card">
                <div className="card-header">
                    <h2 className="card-header-title">
                        Editar Livro: {livro.titulo} (ID: {livro.id})
                    </h2>

                    <button className="btn-voltar" onClick={() => navigate("/consulta/admin")}>
                        Voltar
                    </button>
                </div>

                {message && (
                    <div className={`message ${message.type}`}>
                        {message.text}
                    </div>
                )}

                <form onSubmit={handleUpdate} className="form-grid">
                    {/* Título */}
                    <div className="form-group full">
                        <label>Título *</label>
                        <input
                            name="titulo"
                            value={formState.titulo}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Título da Série</label>
                        <input
                            name="tituloSerie"
                            value={formState.tituloSerie}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label>Número de Registro</label>
                        <input
                            name="numeroRegistro"
                            value={formState.numeroRegistro}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label>Autor Pessoal</label>
                        <input
                            name="autorPessoal"
                            value={formState.autorPessoal}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label>Segundo Autor</label>
                        <input
                            name="segundoAutor"
                            value={formState.segundoAutor}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label>ISBN *</label>
                        <input
                            name="isbn"
                            value={formState.isbn}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label>Número de Chamada</label>
                        <input
                            name="numeroChamada"
                            value={formState.numeroChamada}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label>Edição</label>
                        <input
                            name="edicao"
                            value={formState.edicao}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label>Línguas</label>
                        <input
                            name="linguas"
                            value={formState.linguas}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group full">
                        <label>Área de Descrição Física</label>
                        <input
                            name="areaDescricaoFisica"
                            value={formState.areaDescricaoFisica}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label>Área de Publicação</label>
                        <input
                            name="areaPublicacao"
                            value={formState.areaPublicacao}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label>Assunto Tópico</label>
                        <input
                            name="assuntoTopico"
                            value={formState.assuntoTopico}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-group">
                        <label>Categoria</label>
                        <select
                            name="categoria"
                            value={formState.categoria}
                            onChange={handleChange}
                        >
                            {categories.map((c) => (
                                <option key={c}>{c}</option>
                            ))}
                        </select>
                    </div>

                    <div className="form-group">
                        <label>Quantidade *</label>
                        <input
                            type="number"
                            name="quantidade"
                            value={formState.quantidade}
                            min={1}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="form-actions full">
                        <button
                            type="button"
                            className="btn-delete"
                            onClick={handleDelete}
                            disabled={isSaving}
                        >
                            {isSaving ? "Deletando..." : "Deletar Livro"}
                        </button>

                        <button
                            type="submit"
                            className="btn-primary"
                            disabled={isSaving}
                        >
                            {isSaving ? "Salvando..." : "Atualizar Livro"}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default EditarLivroAdmin;
