import { useEffect, useState } from "react";
import "./style.css";
import { Search, BookOpen, Filter, X } from "lucide-react";
import Header from "../../components/header";

interface Book {
    id: number;
    titulo: string;
    autoresFormatados: string;
    isbn: string;
    assuntos: string[];
    anoPublicacao: number;
    disponivel: boolean;
    exemplares: number;
}

const categories = ["Todas", "Literatura Brasileira", "Ficção Científica", "Infantojuvenil", "Ficção"];

function HomePage() {

    const [books, setBooks] = useState<Book[]>([]);
    const [filteredBooks, setFilteredBooks] = useState<Book[]>([]);
    const [searchTerm, setSearchTerm] = useState("");
    const [selectedCategory, setSelectedCategory] = useState("Todas");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    // Busca todos os livros ao carregar a página
    useEffect(() => {
        fetchBooks();
    }, []);

    const fetchBooks = async () => {
        try {
            setLoading(true);
            setError(null);
            const response = await fetch("http://localhost:8080/livro");
            if (!response.ok) throw new Error("Erro ao buscar livros");
            const data = await response.json();
            setBooks(data);
            setFilteredBooks(data);
        } catch (err) {
            setError("Não foi possível carregar os livros.");
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleSearch = async () => {
        try {
            setLoading(true);
            setError(null);

            // Se não houver termo de busca, busca todos os livros novamente
            if (!searchTerm.trim()) {
                fetchBooks();
                return;
            }

            const response = await fetch(`http://localhost:8080/livro/filtrar?titulo=${encodeURIComponent(searchTerm)}`);
            if (!response.ok) throw new Error("Erro ao buscar livros filtrados");
            const data = await response.json();

            let results = data;

            if (selectedCategory !== "Todas") {
                results = results.filter((book: Book) =>
                    book.assuntos.includes(selectedCategory)
                )
            }

            setFilteredBooks(results);
        } catch (err) {
            setError("Erro ao buscar livros filtrados.");
            console.error(err);
        } finally {
            setLoading(false);
        }

    };

    const clearFilters = () => {
        setSearchTerm("");
        setSelectedCategory("Todas");
        fetchBooks();
    };


    return (

        <div className="busca-container">
            <Header />
            {/* Main Content */}
            <main className="busca-main">
                <div className="busca-content">
                    {/* Page Header */}
                    <div className="busca-page-header">
                        <h1>Buscar Livros</h1>
                        <p>Pesquise por título, autor, ISBN ou categoria no acervo da biblioteca</p>
                    </div>

                    {/* Search Filters */}
                    <div className="busca-card busca-filters">
                        <div className="filter-header">
                            <h2>
                                <Filter size={20} />
                                Filtros de Busca
                            </h2>
                            <p>Refine sua pesquisa usando os filtros abaixo</p>
                        </div>
                        <div className="filter-content">
                            <div className="filter-grid">
                                <div className="filter-group">
                                    <label htmlFor="search">Buscar por título, autor ou ISBN</label>
                                    <input
                                        id="search"
                                        type="text"
                                        placeholder="Digite sua busca..."
                                        value={searchTerm}
                                        onChange={(e) => setSearchTerm(e.target.value)}
                                        onKeyDown={(e) => e.key === "Enter" && handleSearch()}
                                        className="filter-input"
                                    />
                                </div>
                                <div className="filter-group">
                                    <label htmlFor="category">Categoria</label>
                                    <select
                                        id="category"
                                        value={selectedCategory}
                                        onChange={(e) => setSelectedCategory(e.target.value)}
                                        className="filter-select"
                                    >
                                        {categories.map((cat) => (
                                            <option key={cat} value={cat}>
                                                {cat}
                                            </option>
                                        ))}
                                    </select>
                                </div>
                            </div>
                            <div className="filter-buttons">
                                <button onClick={handleSearch} className="btn-search">
                                    <Search size={18} />
                                    Buscar
                                </button>
                                <button onClick={clearFilters} className="btn-clear">
                                    <X size={18} />
                                    Limpar
                                </button>
                            </div>
                        </div>
                    </div>

                    {/* Results */}
                    <div className="busca-results">
                        <div className="results-header">
                            <h2>Resultados {filteredBooks.length > 0 && `(${filteredBooks.length})`}</h2>
                        </div>

                        {filteredBooks.length === 0 ? (
                            <div className="busca-card empty-results">
                                <div className="empty-content">
                                    <BookOpen size={48} />
                                    <p className="empty-title">Nenhum livro encontrado</p>
                                    <p className="empty-description">Tente ajustar os filtros ou fazer uma nova busca</p>
                                </div>
                            </div>
                        ) : (
                            <div className="books-grid">
                                {filteredBooks.map((book) => (
                                    <div key={book.id} className="busca-card book-card">
                                        <div className="book-content">
                                            <div className="book-header">
                                                <div className="book-icon">
                                                    <BookOpen size={20} />
                                                </div>
                                                <div className="book-title-section">
                                                    <h3>{book.titulo}</h3>
                                                    <p className="book-author">{book.autoresFormatados}</p>
                                                </div>
                                            </div>

                                            <div className="book-info">
                                                <span>ISBN: {book.isbn}</span>
                                                <span>•</span>
                                                <span>Ano: {book.anoPublicacao}</span>
                                            </div>
                                            <div className="book-badges">
                                                <div className="categories-container">
                                                    {book.assuntos.map((assunto, index) => (
                                                        <span key={index} className="badge badge-category">
                                                            {assunto}
                                                        </span>
                                                    ))}
                                                </div>
                                                {book.disponivel ? (
                                                    <span className="badge badge-available">
                                                        Disponível
                                                    </span>
                                                ) : (
                                                    <span className="badge badge-unavailable">Indisponível</span>
                                                )}
                                            </div>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        )}
                    </div>
                </div>
            </main>

            {/* Footer */}
            <footer className="busca-footer">
                <p>© 2025 CEDUP Hermann Hering - Biblioteca Escolar</p>
            </footer>
        </div>
    );

}

export default HomePage;