import "./style.css";
import logo from "../../assets/cedup-logo.png";
import bookIcon from "../../assets/book-icon.png";
import background from "../../assets/library-bg.jpeg";

function Login() {
  return (
    <div className="login-container">
      <div className="login-left">
        <img src={logo} alt="CEDUP Hermann Hering" className="login-logo" />

        <div className="login-title">
          <img src={bookIcon} alt="Ícone de livro" className="book-icon" />
          <h1>Bem-vindo(a)!</h1>
        </div>

        <form className="login-form">
          <label htmlFor="matricula">Matrícula</label>
          <input
            type="text"
            id="matricula"
            placeholder="Digite sua matrícula"
          />
          <button type="submit">Entrar</button>
        </form>

        <p className="login-footer">
          Acesso exclusivo para alunos e funcionários
        </p>
      </div>

      <div
        className="login-right"
        style={{ backgroundImage: `url(${background})` }}
      ></div>
    </div>
  );
}

export default Login;
