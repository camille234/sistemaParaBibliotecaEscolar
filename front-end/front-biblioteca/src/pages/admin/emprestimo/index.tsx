import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Header from "../../../components/admin/header";
import "./style.css";

// Payload que será enviado para o endpoint
interface RealizarEmprestimoPayload {
  matriculaAluno: string;
  isbnLivro: string;
  dataRetirada: string;             // YYYY-MM-DD
  dataDevolucaoPrevista: string;    // YYYY-MM-DD
  dataDevolucaoReal: string | null;
  status: string;                   // "PENDENTE"
}

function formatDateYYYYMMDD(date: Date) {
  return date.toISOString().slice(0, 10);
}

function addDays(date: Date, days: number) {
  const d = new Date(date);
  d.setDate(d.getDate() + days);
  return d;
}

function RegistrarEmprestimoAdmin() {
  const navigate = useNavigate();

  const [isbn, setIsbn] = useState<string>("");
  const [matricula, setMatricula] = useState<string>("");
  const [isSaving, setIsSaving] = useState<boolean>(false);
  const [message, setMessage] = useState<{ type: "success" | "error"; text: string } | null>(null);

  // Validação simples: isbn e matrícula não vazios
  const validate = () => {
    if (!isbn.trim()) {
      setMessage({ type: "error", text: "Informe o ISBN do livro." });
      return false;
    }
    if (!matricula.trim()) {
      setMessage({ type: "error", text: "Informe a matrícula do aluno." });
      return false;
    }
    return true;
  };

  const handleRegister = async (e: React.FormEvent) => {
    e.preventDefault();
    setMessage(null);

    if (!validate()) return;

    setIsSaving(true);

    // monta datas
    const hoje = new Date();
    const retirada = formatDateYYYYMMDD(hoje);
    const prevista = formatDateYYYYMMDD(addDays(hoje, 14));

    const payload: RealizarEmprestimoPayload = {
      matriculaAluno: matricula.trim(),
      isbnLivro: isbn.trim(),
      dataRetirada: retirada,
      dataDevolucaoPrevista: prevista,
      dataDevolucaoReal: null,
      status: "PENDENTE",
    };

    try {
      const resp = await fetch("http://localhost:8080/emprestimos/realizar", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      if (!resp.ok) {
        // tenta extrair a mensagem do backend
        let errText = "Erro ao registrar empréstimo.";
        try {
          const errJson = await resp.json();
          if (errJson?.message) errText = errJson.message;
        } catch { /* ignore */ }
        throw new Error(errText);
      }

      setMessage({ type: "success", text: "Empréstimo realizado com sucesso! Redirecionando..." });

      // limpa campos
      setIsbn("");
      setMatricula("");

      setTimeout(() => navigate("home/admin"), 1400);
    } catch (error) {
      const text = error instanceof Error ? error.message : "Erro desconhecido.";
      setMessage({ type: "error", text });
    } finally {
      setIsSaving(false);
    }
  };

  return (
    <div className="admin-container">
      <Header />

      <main className="admin-content">
        <h1 className="admin-title">Painel Administrativo</h1>
        <p className="admin-subtitle">Gerencie o acervo e os empréstimos da biblioteca</p>

        <div className="card-emprestimo">
          <div className="card-header">
            <h2>Registrar Empréstimo</h2>
            <button className="btn-voltar" onClick={() => navigate("/home/admin")}>Voltar</button>
          </div>

          {message && <div className={`message ${message.type}`}>{message.text}</div>}

          <form onSubmit={handleRegister} className="form-emprestimo">
            {/* ISBN */}
            <div className="form-group full">
              <label htmlFor="isbn">ISBN do Livro *</label>
              <input
                id="isbn"
                name="isbn"
                type="text"
                placeholder="Ex: 978-853590***-5"
                value={isbn}
                onChange={(e) => setIsbn(e.target.value)}
                disabled={isSaving}
              />
            </div>

            {/* Matrícula */}
            <div className="form-group full">
              <label htmlFor="matriculaAluno">Matrícula do Aluno *</label>
              <input
                id="matriculaAluno"
                name="matriculaAluno"
                type="text"
                placeholder="Ex: 2022003003"
                value={matricula}
                onChange={(e) => setMatricula(e.target.value)}
                disabled={isSaving}
              />
            </div>

            <div className="form-actions full">
              <button type="submit" className="btn-primary" disabled={isSaving}>
                {isSaving ? "Processando..." : "Registrar Empréstimo"}
              </button>
            </div>
          </form>
        </div>
      </main>
    </div>
  );
}

export default RegistrarEmprestimoAdmin;
