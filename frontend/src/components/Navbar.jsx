import { NavLink } from 'react-router-dom'
import './Navbar.css'

export default function Navbar() {
  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <span className="material-symbols-outlined navbar-icon">local_hospital</span>
        <span className="navbar-title">Clínica Multidisciplinar</span>
      </div>
      <ul className="navbar-links">
        <li>
          <NavLink to="/" end className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>
            Dashboard
          </NavLink>
        </li>
        <li>
          <NavLink to="/profissionais" className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>
            Profissionais
          </NavLink>
        </li>
        <li>
          <NavLink to="/atendimentos" className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>
            Atendimentos
          </NavLink>
        </li>
        <li>
          <NavLink to="/exames" className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>
            Exames Lab
          </NavLink>
        </li>
      </ul>
    </nav>
  )
}
