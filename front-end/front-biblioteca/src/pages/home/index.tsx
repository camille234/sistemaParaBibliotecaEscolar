import "./style.css";

function Home() {
  return (
    <div className="home-container">
      <header className="home-header">
        <h1>Bem-vindo(a) à Biblioteca CEDUP</h1>
      </header>

      <main className="home-main">
        <p>Explore nossos livros, acesse seu perfil e descubra novos conhecimentos!</p>
        <button className="logout-btn">Sair</button>
      </main>
    </div>
  );
}

export default Home;