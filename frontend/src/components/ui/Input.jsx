import './Input.css'

export default function Input({ icon, className = '', ...props }) {
  return (
    <div className={`ui-input-wrap ${icon ? 'ui-input-wrap--icon' : ''} ${className}`.trim()}>
      {icon && <span className="material-symbols-outlined ui-input-icon">{icon}</span>}
      <input className="ui-input" {...props} />
    </div>
  )
}
