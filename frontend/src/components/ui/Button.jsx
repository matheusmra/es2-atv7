import { Link } from 'react-router-dom'
import './Button.css'

export default function Button({
  children,
  variant = 'primary',
  size,
  type = 'button',
  disabled = false,
  onClick,
  to,
  className = '',
  ...props
}) {
  const cls = ['ui-btn', `ui-btn--${variant}`, size && `ui-btn--${size}`, className]
    .filter(Boolean)
    .join(' ')

  if (to) {
    return (
      <Link to={to} className={cls} {...props}>
        {children}
      </Link>
    )
  }

  return (
    <button type={type} className={cls} disabled={disabled} onClick={onClick} {...props}>
      {children}
    </button>
  )
}
