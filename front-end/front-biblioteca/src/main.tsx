import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'

//pages
import './index.css'
import Login from './pages/login'
import HomePage from './pages/home'
import AdminLogin from './pages/admin/login'
import AdminHome from './pages/admin/home'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/home" element={<HomePage />} />
        <Route path='/admin' element={<AdminLogin />}/>
        <Route path='/home/admin' element={<AdminHome />}/>
      </Routes>
    </Router>
  </StrictMode>,
)