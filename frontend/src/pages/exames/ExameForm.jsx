import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { getExameById, createExame, updateExame } from '../../api/exames'
import { getAtendimentos } from '../../api/atendimentos'
import Button from '../../components/ui/Button'
import Input from '../../components/ui/Input'
import Select from '../../components/ui/Select'

const EMPTY = { descricao: '', atendimentoId: '' }

export default function ExameForm() {
  const { id } = useParams()
  const navigate = useNavigate()
  const isEdit = Boolean(id)

  const [form, setForm] = useState(EMPTY)
  const [atendimentos, setAtendimentos] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  const atendimentoOptions = [
    { value: '', label: 'Nenhum' },
    ...atendimentos.map((a) => ({
      value: String(a.id),
      label: `#${a.id} — ${a.data} ${a.horario} (${a.profissionalNome ?? '?'})`,
    })),
  ]

  useEffect(() => {
    getAtendimentos().then((res) => setAtendimentos(res.data)).catch(() => {})

    if (!isEdit) return
    setLoading(true)
    getExameById(id)
      .then((res) => {
        const ex = res.data
        setForm({
          descricao: ex.descricao ?? '',
          atendimentoId: ex.atendimentoId ? String(ex.atendimentoId) : '',
        })
      })
      .catch(() => setError('Exame não encontrado.'))
      .finally(() => setLoading(false))
  }, [id, isEdit])

  const handleChange = (e) => {
    setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError(null)
    const payload = {
      descricao: form.descricao,
      atendimentoId: form.atendimentoId ? Number(form.atendimentoId) : null,
    }
    try {
      setLoading(true)
      if (isEdit) {
        await updateExame(id, payload)
      } else {
        await createExame(payload)
      }
      navigate('/exames')
    } catch (err) {
      setError(err.response?.data?.message || 'Erro ao salvar exame.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="page">
      <div className="page-header">
        <h1 className="page-title">{isEdit ? 'Editar Exame' : 'Novo Exame Laboratorial'}</h1>
      </div>

      {error && <div className="alert alert-error">{error}</div>}

      <form className="card form-card" onSubmit={handleSubmit}>
        <div className="form-group">
          <label className="form-label">Descrição do Exame</label>
          <Input
            icon="science"
            name="descricao"
            value={form.descricao}
            onChange={handleChange}
            required
            placeholder="Ex: Hemograma completo"
          />
        </div>

        <div className="form-group">
          <label className="form-label">Atendimento vinculado</label>
          <Select
            name="atendimentoId"
            value={form.atendimentoId}
            onChange={handleChange}
            options={atendimentoOptions}
            placeholder="Selecione um atendimento..."
          />
        </div>

        <div className="form-actions">
          <Button variant="ghost" type="button" onClick={() => navigate('/exames')}>
            Cancelar
          </Button>
          <Button type="submit" disabled={loading}>
            {loading ? 'Salvando...' : isEdit ? 'Salvar Alterações' : 'Cadastrar Exame'}
          </Button>
        </div>
      </form>
    </div>
  )
}
