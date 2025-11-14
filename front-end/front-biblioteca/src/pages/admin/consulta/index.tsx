import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import searchIcon from "../../../assets/lupa_branca.png";
import editIcon from "../../../assets/edicao.png";
import Header from "../../../components/admin/header";
import "./style.css";

interface Livro {
  id: number;
  titulo: string;
  autoresFormatados: string;
  editora: string;
  disponivel: boolean;
}

const categories = ["Título", "ISBN"];

function ConsultaLivrosAdmin() {
  const navigate = useNavigate();

  const [livros, setLivros] = useState<Livro[]>([]);
  const [livrosFiltrados, setLivrosFiltrados] = useState<Livro[]>([]);
  const [termoBusca, setTermoBusca] = useState<string>("");
  const [categoria, setCategoria] = useState<string>("Título");
  const [isLoading, setIsLoading] = useState<boolean>(true);

  // Busca livros na API (todos)
  const fetchLivros = async () => {
    try {
      const response = await fetch("http://localhost:8080/livro");
      if (!response.ok) throw new Error("Erro ao buscar livros");
      const data: Livro[] = await response.json();
      setLivros(data);
      setLivrosFiltrados(data);
    } catch (error) {
      console.error("Erro ao carregar livros:", error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchLivros();
  }, []);

  // Busca filtrada por categoria quando o botão é clicado
  const handleSearch = async () => {
    if (!termoBusca.trim()) {
      setLivrosFiltrados(livros);
      return;
    }

    try {
      setIsLoading(true);
      let url = "";

      if (categoria === "ISBN") {
        url = `http://localhost:8080/livro/filtrar?isbn=${termoBusca}`;
      }

      if (categoria === "Título") {
        const termo = termoBusca.toLowerCase();
        const resultados = livros.filter(
          (livro) =>
            livro.titulo.toLowerCase().includes(termo) ||
            livro.editora.toLowerCase().includes(termo)
        );
        setLivrosFiltrados(resultados);
      } else {
        const response = await fetch(url);
        if (!response.ok) throw new Error("Erro na busca filtrada");
        const data: Livro[] = await response.json();
        setLivrosFiltrados(data);
      }
    } catch (error) {
      console.error("Erro na busca filtrada:", error);
      setLivrosFiltrados([]);
    } finally {
      setIsLoading(false);
    }
  };

  // Redireciona para página de edição
  const handleEdit = (livroId: number) => {
    navigate(`/editarLivro/admin/${livroId}`);
  };

  return (
    <div className="admin-container">
      <Header />

      <main className="admin-content">
        <h1 className="admin-title">Painel Administrativo</h1>
        <p className="admin-subtitle">
          Gerencie o acervo e empréstimos da biblioteca
        </p>

        <div className="consulta-card-container">
          <div className="consulta-header">
            <h2>Consultar e Editar livros</h2>
          </div>

          {/* Barra de Busca */}
          <div className="search-bar-container">
            <input
              type="text"
              placeholder={`Buscar por ${categoria.toLowerCase()}...`}
              value={termoBusca}
              onChange={(e) => setTermoBusca(e.target.value)}
              className="search-input"
            />

            <select
              id="category"
              className="filter-select"
              value={categoria}
              onChange={(e) => setCategoria(e.target.value)}
            >
              {categories.map((cat) => (
                <option key={cat} value={cat}>
                  {cat}
                </option>
              ))}
            </select>

            <button className="search-button" onClick={handleSearch}>
              <img src={searchIcon} alt="Buscar" />
            </button>
          </div>

          {/* Tabela de Livros */}
          <div className="livros-table-container">
            {isLoading ? (
              <p className="loading-message">Carregando livros...</p>
            ) : livrosFiltrados.length === 0 ? (
              <p className="no-results">Nenhum resultado encontrado.</p>
            ) : (
              <table className="livros-table">
                <thead>
                  <tr>
                    <th>Título</th>
                    <th>Autor</th>
                    <th>Editora</th>
                    <th>Status</th>
                    <th>Ação</th>
                  </tr>
                </thead>
                <tbody>
                  {livrosFiltrados.map((livro) => (
                    <tr key={livro.id}>
                      <td>{livro.titulo}</td>
                      <td>{livro.autoresFormatados}</td>
                      <td>{livro.editora}</td>
                      <td>
                        <span
                          className={`status-tag ${
                            livro.disponivel
                              ? "status-disponivel"
                              : "status-indisponivel"
                          }`}
                        >
                          {livro.disponivel ? "Disponível" : "Indisponível"}
                        </span>
                      </td>
                      <td className="action-cell">
                        <button
                          className="edit-button"
                          onClick={() => handleEdit(livro.id)}
                        >
                          <img src={editIcon} alt="Editar" />
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )}
          </div>
        </div>
      </main>
    </div>
  );
}

export default ConsultaLivrosAdmin;
