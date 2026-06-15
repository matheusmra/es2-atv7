import { useState, useRef, useEffect } from 'react'
import './Select.css'

export default function Select({
  name,
  value,
  onChange,
  options = [],
  placeholder = 'Selecione...',
  className = '',
}) {
  const [open, setOpen] = useState(false)
  const ref = useRef(null)

  const selected = options.find((o) => String(o.value) === String(value))

  useEffect(() => {
    const close = (e) => {
      if (ref.current && !ref.current.contains(e.target)) setOpen(false)
    }
    document.addEventListener('mousedown', close)
    return () => document.removeEventListener('mousedown', close)
  }, [])

  const pick = (val) => {
    onChange({ target: { name, value: val } })
    setOpen(false)
  }

  return (
    <div ref={ref} className={`ui-select ${open ? 'ui-select--open' : ''} ${className}`.trim()}>
      <button type="button" className="ui-select-trigger" onClick={() => setOpen((v) => !v)}>
        <span className={selected ? 'ui-select-value' : 'ui-select-placeholder'}>
          {selected ? selected.label : placeholder}
        </span>
        <span className="material-symbols-outlined ui-select-chevron">expand_more</span>
      </button>

      {open && (
        <ul className="ui-select-dropdown" role="listbox">
          {options.map((opt) => {
            const isActive = String(opt.value) === String(value)
            return (
              <li
                key={String(opt.value)}
                role="option"
                aria-selected={isActive}
                className={`ui-select-option ${isActive ? 'ui-select-option--active' : ''}`}
                onClick={() => pick(opt.value)}
              >
                <span className="ui-select-option-label">{opt.label}</span>
                {isActive && (
                  <span className="material-symbols-outlined ui-select-check">check</span>
                )}
              </li>
            )
          })}
        </ul>
      )}
    </div>
  )
}
