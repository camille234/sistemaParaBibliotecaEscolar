import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'

//pages
import './index.css'
import Login from './pages/login'
import HomePage from './pages/home'
import AdminLogin from './pages/admin/login'
import AdminHome from './pages/admin/home'
import ConsultaLivrosAdmin from './pages/admin/consulta'
import EditarLivroAdmin from './pages/admin/atualizar'
import RegistrarEmprestimoAdmi from './pages/admin/emprestimo'
import CadastrarLivroAdmin from './pages/admin/cadastrar'
import RegistrarDevolucaoAdmin from './pages/admin/devolucao'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/home" element={<HomePage />} />
        <Route path='/admin' element={<AdminLogin />}/>
        <Route path='/home/admin' element={<AdminHome />}/>
        <Route path='/consulta/admin' element={<ConsultaLivrosAdmin />}/>
        <Route path='/editarLivro/admin/:id' element={<EditarLivroAdmin />} />
        <Route path='/emprestimo/admin' element={<RegistrarEmprestimoAdmi />}/>
        <Route path='/cadastrarLivro/admin' element={<CadastrarLivroAdmin />}/>
        <Route path='/devolucaoLivro/admin' element={<RegistrarDevolucaoAdmin />}/>
      </Routes>
    </Router>
  </StrictMode>,
)