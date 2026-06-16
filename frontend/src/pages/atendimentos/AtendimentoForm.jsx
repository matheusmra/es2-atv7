import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { getAtendimentoById, createAtendimento, updateAtendimento } from '../../api/atendimentos'
import { getProfissionais } from '../../api/profissionais'
import Button from '../../components/ui/Button'
import Input from '../../components/ui/Input'
import Select from '../../components/ui/Select'
import Textarea from '../../components/ui/Textarea'

const RECEITA_LABEL = {
  MEDICO: 'Remédio prescrito',
  FISIOTERAPEUTA: 'Atividade Física recomendada',
  PSICOLOGO: 'Atividades Mentais recomendadas',
}

const EMPTY = {
  data: '',
  horario: '',
  problemaTexto: '',
  receitaSaude: '',
  profissionalId: '',
}

export default function AtendimentoForm() {
  const { id } = useParams()
  const navigate = useNavigate()
  const isEdit = Boolean(id)

  const [form, setForm] = useState(EMPTY)
  const [profissionais, setProfissionais] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  const selectedProfissional = profissionais.find(
    (p) => String(p.id) === String(form.profissionalId)
  )
  const receitaLabel = selectedProfissional
    ? RECEITA_LABEL[selectedProfissional.categoria]
    : 'Receita / Prescrição'

  const profissionalOptions = profissionais.map((p) => ({
    value: String(p.id),
    label: `${p.nome} — ${p.categoria}`,
  }))

  useEffect(() => {
    getProfissionais().then((res) => setProfissionais(res.data)).catch(() => {})

    if (!isEdit) return
    setLoading(true)
    getAtendimentoById(id)
      .then((res) => {
        const a = res.data
        setForm({
          data: a.data ?? '',
          horario: a.horario ?? '',
          problemaTexto: a.problemaTexto ?? '',
          receitaSaude: a.receitaSaude ?? '',
          profissionalId: a.profissionalId ? String(a.profissionalId) : '',
        })
      })
      .catch(() => setError('Atendimento não encontrado.'))
      .finally(() => setLoading(false))
  }, [id, isEdit])

  const handleChange = (e) => {
    setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError(null)
    const payload = {
      data: form.data,
      horario: form.horario,
      problemaTexto: form.problemaTexto,
      receitaSaude: form.receitaSaude,
      profissionalId: Number(form.profissionalId),
    }
    try {
      setLoading(true)
      if (isEdit) {
        await updateAtendimento(id, payload)
      } else {
        await createAtendimento(payload)
      }
      navigate('/atendimentos')
    } catch (err) {
      setError(err.response?.data?.message || 'Erro ao salvar atendimento.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="page">
      <div className="page-header">
        <h1 className="page-title">{isEdit ? 'Editar Atendimento' : 'Novo Atendimento'}</h1>
      </div>

      {error && <div className="alert alert-error">{error}</div>}

      <form className="card form-card" onSubmit={handleSubmit}>
        <div className="form-group">
          <label className="form-label">Profissional</label>
          <Select
            name="profissionalId"
            value={form.profissionalId}
            onChange={handleChange}
            options={profissionalOptions}
            placeholder="Selecione um profissional..."
          />
        </div>

        <div className="form-row">
          <div className="form-group">
            <label className="form-label">Data</label>
            <Input
              icon="calendar_today"
              type="date"
              name="data"
              value={form.data}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <label className="form-label">Horário</label>
            <Input
              icon="schedule"
              type="time"
              name="horario"
              value={form.horario}
              onChange={handleChange}
              required
            />
          </div>
        </div>

        <div className="form-group">
          <label className="form-label">Problema Relatado</label>
          <Textarea
            name="problemaTexto"
            minLength={10}
            maxLength={1000}
            value={form.problemaTexto}
            onChange={handleChange}
            required
            rows={3}
            placeholder="Descreva o problema do paciente... (mín. 10 caracteres)"
          />
        </div>

        <div className="form-group">
          <label className="form-label">{receitaLabel}</label>
          <Textarea
            name="receitaSaude"
            minLength={5}
            maxLength={2000}
            value={form.receitaSaude}
            onChange={handleChange}
            required
            rows={3}
            placeholder={`Preencha a ${receitaLabel.toLowerCase()}... (mín. 5 caracteres)`}
          />
        </div>

        <div className="form-actions">
          <Button variant="ghost" type="button" onClick={() => navigate('/atendimentos')}>
            Cancelar
          </Button>
          <Button type="submit" disabled={loading}>
            {loading ? 'Salvando...' : isEdit ? 'Salvar Alterações' : 'Registrar Atendimento'}
          </Button>
        </div>
      </form>
    </div>
  )
}

