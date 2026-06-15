import { Link } from 'react-router-dom'
import './Home.css'

const CARDS = [
  {
    to: '/profissionais',
    icon: 'groups',
    title: 'Profissionais',
    description: 'Gerencie médicos, fisioterapeutas e psicólogos da clínica.',
    color: 'card-blue',
  },
  {
    to: '/atendimentos',
    icon: 'event_note',
    title: 'Atendimentos',
    description: 'Registre e acompanhe os atendimentos realizados na clínica.',
    color: 'card-green',
  },
  {
    to: '/exames',
    icon: 'biotech',
    title: 'Exames Lab',
    description: 'Cadastre e vincule exames laboratoriais aos atendimentos.',
    color: 'card-purple',
  },
]

export default function Home() {
  return (
    <div className="page">
      <div className="home-hero">
        <div className="home-hero-tag">Sistema de Gestão</div>
        <h1 className="home-title">Clínica Multidisciplinar</h1>
        <p className="home-subtitle">
          Gerencie profissionais, atendimentos e exames em um só lugar.
        </p>
      </div>
      <div className="home-grid">
        {CARDS.map(({ to, icon, title, description, color }) => (
          <Link key={to} to={to} className={`home-card ${color}`}>
            <div className="home-card-icon-wrap">
              <span className="material-symbols-outlined">{icon}</span>
            </div>
            <div className="home-card-body">
              <h2 className="home-card-title">{title}</h2>
              <p className="home-card-desc">{description}</p>
            </div>
            <span className="material-symbols-outlined home-card-arrow">arrow_forward</span>
          </Link>
        ))}
      </div>
    </div>
  )
}
