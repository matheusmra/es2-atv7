import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Navbar from './components/Navbar'
import Home from './pages/Home'
import ProfissionaisList from './pages/profissionais/ProfissionaisList'
import ProfissionalForm from './pages/profissionais/ProfissionalForm'
import AtendimentosList from './pages/atendimentos/AtendimentosList'
import AtendimentoForm from './pages/atendimentos/AtendimentoForm'
import ExamesList from './pages/exames/ExamesList'
import ExameForm from './pages/exames/ExameForm'

export default function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <main className="main-content">
        <Routes>
          <Route path="/" element={<Home />} />

          <Route path="/profissionais" element={<ProfissionaisList />} />
          <Route path="/profissionais/novo" element={<ProfissionalForm />} />
          <Route path="/profissionais/:id/editar" element={<ProfissionalForm />} />

          <Route path="/atendimentos" element={<AtendimentosList />} />
          <Route path="/atendimentos/novo" element={<AtendimentoForm />} />
          <Route path="/atendimentos/:id/editar" element={<AtendimentoForm />} />

          <Route path="/exames" element={<ExamesList />} />
          <Route path="/exames/novo" element={<ExameForm />} />
          <Route path="/exames/:id/editar" element={<ExameForm />} />
        </Routes>
      </main>
    </BrowserRouter>
  )
}
