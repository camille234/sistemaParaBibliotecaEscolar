import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./style.css";
import logo from "../../../assets/cedup-logo.png";
import bookIcon from "../../../assets/book-icon.png";
import background from "../../../assets/library-bg.jpeg";

function AdminLogin() {
  const [senha, setSenha] = useState("");
  const [erro, setErro] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErro("");

    try {
      const response = await fetch("http://localhost:8080/auth/bibliotecaria", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ senha }),
      });

      if (response.ok) {
        const data = await response.json();
        console.log("Aluno autenticado:", data);
        navigate("/home/admin"); // redireciona se der certo
      } else {
        setErro("Senha inválida!");
      }
    } catch (error) {
      console.error("Erro na autenticação:", error);
      setErro("Erro de conexão com o servidor.");
    }
  };

  return (
    <div className="login-container">
      <div className="login-left">
        <img src={logo} alt="CEDUP Hermann Hering" className="login-logo" />

        <div className="login-title">
          <img src={bookIcon} alt="Ícone de livro" className="book-icon" />
          <h1>Bem-vindo(a)!</h1>
        </div>

        <form className="login-form" onSubmit={handleSubmit}>
          <label htmlFor="senha">Senha</label>
          <input
            type="text"
            id="senha"
            placeholder="Digite sua senha"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
          />

          {erro && <p className="login-error">{erro}</p>}

          <button type="submit">Entrar</button>
        </form>

        <p className="login-footer">
          Acesso exclusivo para administradores
        </p>
      </div>

      <div
        className="login-right"
        style={{ backgroundImage: `url(${background})` }}
      ></div>
    </div>
  );
}

export default AdminLogin;
