import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import {
  getProfissionalById,
  createProfissional,
  updateProfissional,
} from '../../api/profissionais'
import Button from '../../components/ui/Button'
import Input from '../../components/ui/Input'
import Select from '../../components/ui/Select'

const CATEGORIAS_OPTIONS = [
  { value: 'MEDICO', label: 'Médico' },
  { value: 'FISIOTERAPEUTA', label: 'Fisioterapeuta' },
  { value: 'PSICOLOGO', label: 'Psicólogo' },
]

const EMPTY = { nome: '', telefone: '', endereco: '', categoria: 'MEDICO' }

export default function ProfissionalForm() {
  const { id } = useParams()
  const navigate = useNavigate()
  const isEdit = Boolean(id)

  const [form, setForm] = useState(EMPTY)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  useEffect(() => {
    if (!isEdit) return
    setLoading(true)
    getProfissionalById(id)
      .then((res) => setForm(res.data))
      .catch(() => setError('Profissional não encontrado.'))
      .finally(() => setLoading(false))
  }, [id, isEdit])

  const handleChange = (e) => {
    setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError(null)
    try {
      setLoading(true)
      if (isEdit) {
        await updateProfissional(id, form)
      } else {
        await createProfissional(form)
      }
      navigate('/profissionais')
    } catch (err) {
      setError(err.response?.data?.message || 'Erro ao salvar profissional.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="page">
      <div className="page-header">
        <h1 className="page-title">{isEdit ? 'Editar Profissional' : 'Novo Profissional'}</h1>
      </div>

      {error && <div className="alert alert-error">{error}</div>}

      {loading && isEdit ? (
        <div className="spinner-wrap"><div className="spinner" /></div>
      ) : (
        <form className="card form-card" onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label">Nome</label>
            <Input
              icon="person"
              name="nome"
              value={form.nome}
              onChange={handleChange}
              required
              placeholder="Nome completo"
            />
          </div>

          <div className="form-row">
            <div className="form-group">
              <label className="form-label">Telefone</label>
              <Input
                icon="phone"
                name="telefone"
                value={form.telefone}
                onChange={handleChange}
                required
                placeholder="(00) 00000-0000"
              />
            </div>
            <div className="form-group">
              <label className="form-label">Categoria</label>
              <Select
                name="categoria"
                value={form.categoria}
                onChange={handleChange}
                options={CATEGORIAS_OPTIONS}
              />
            </div>
          </div>

          <div className="form-group">
            <label className="form-label">Endereço</label>
            <Input
              icon="location_on"
              name="endereco"
              value={form.endereco}
              onChange={handleChange}
              required
              placeholder="Rua, número, cidade"
            />
          </div>

          <div className="form-actions">
            <Button variant="ghost" type="button" onClick={() => navigate('/profissionais')}>
              Cancelar
            </Button>
            <Button type="submit" disabled={loading}>
              {loading ? 'Salvando...' : isEdit ? 'Salvar Alterações' : 'Cadastrar'}
            </Button>
          </div>
        </form>
      )}
    </div>
  )
}
