import React from "react";
import "./style.css";
import logo from "../../assets/cedup-logo.png";

const Header: React.FC = () => {
  return (
    <div>
      {/* Header */}
      <header className="busca-header">
        <div className="header-content">
          <a href="/home" className="logo-link">
            <img src={logo} alt="CEDUP Hermann Hering" width={120} height={60} />
          </a>
          <nav className="header-nav">
            <a href="/">
              <button className="nav-button">Início</button>
            </a>
          </nav>
        </div>
      </header>
    </div>
  );
};

export default Header;
