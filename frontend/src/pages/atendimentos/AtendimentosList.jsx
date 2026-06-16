import { useEffect, useState } from 'react'
import { getAtendimentos, deleteAtendimento } from '../../api/atendimentos'
import Button from '../../components/ui/Button'
import Input from '../../components/ui/Input'
import ConfirmModal from '../../components/ConfirmModal'

const RECEITA_LABEL = {
  MEDICO: 'Remédio',
  FISIOTERAPEUTA: 'Atividade Física',
  PSICOLOGO: 'Atividades Mentais',
}

export default function AtendimentosList() {
  const [atendimentos, setAtendimentos] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [searchId, setSearchId] = useState('')
  const [deleteTarget, setDeleteTarget] = useState(null)

  const fetchAll = async () => {
    try {
      setLoading(true)
      const res = await getAtendimentos()
      setAtendimentos(res.data)
    } catch {
      setError('Erro ao carregar atendimentos.')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { fetchAll() }, [])

  const handleSearch = async () => {
    try {
      setLoading(true)
      if (searchId.trim()) {
        const { getAtendimentoById } = await import('../../api/atendimentos')
        const res = await getAtendimentoById(searchId.trim())
        setAtendimentos(res.data ? [res.data] : [])
      } else {
        const res = await getAtendimentos()
        setAtendimentos(res.data)
      }
    } catch {
      setError('Erro ao buscar atendimentos.')
    } finally {
      setLoading(false)
    }
  }

  const handleDelete = async () => {
    try {
      await deleteAtendimento(deleteTarget)
      setDeleteTarget(null)
      fetchAll()
    } catch {
      setError('Erro ao excluir atendimento.')
      setDeleteTarget(null)
    }
  }

  return (
    <div className="page">
      <div className="page-header">
        <h1 className="page-title">Atendimentos</h1>
        <Button to="/atendimentos/novo">
          <span className="material-symbols-outlined">add</span>
          Novo Atendimento
        </Button>
      </div>

      <div className="filters">
        <Input
          icon="tag"
          placeholder="Buscar por ID..."
          value={searchId}
          onChange={(e) => setSearchId(e.target.value)}
        />
        <Button variant="secondary" onClick={handleSearch}>
          <span className="material-symbols-outlined">search</span>
          Buscar
        </Button>
        <Button variant="ghost" onClick={() => { setSearchId(''); fetchAll() }}>
          Limpar
        </Button>
      </div>

      {error && <div className="alert alert-error">{error}</div>}

      {loading ? (
        <div className="spinner-wrap"><div className="spinner" /></div>
      ) : atendimentos.length === 0 ? (
        <div className="empty-state">Nenhum atendimento registrado.</div>
      ) : (
        <div className="table-wrap">
          <table className="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Data</th>
                <th>Horário</th>
                <th>Profissional</th>
                <th>Problema</th>
                <th>Receita / Prescrição</th>
                <th>Ações</th>
              </tr>
            </thead>
            <tbody>
              {atendimentos.map((a) => (
                <tr key={a.id}>
                  <td className="id-col">{a.id}</td>
                  <td>{a.data}</td>
                  <td>{a.horario}</td>
                  <td className="bold">{a.profissionalNome ?? '—'}</td>
                  <td className="truncate">{a.problemaTexto}</td>
                  <td>
                    <span className="receita-label">
                      {RECEITA_LABEL[a.profissionalCategoria] ?? 'Receita'}:
                    </span>{' '}
                    {a.receitaSaude}
                  </td>
                  <td>
                    <div className="action-btns">
                      <Button to={`/atendimentos/${a.id}/editar`} variant="secondary" size="sm">
                        <span className="material-symbols-outlined">edit</span>
                        Editar
                      </Button>
                      <Button variant="danger" size="sm" onClick={() => setDeleteTarget(a.id)}>
                        <span className="material-symbols-outlined">delete</span>
                        Excluir
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {deleteTarget && (
        <ConfirmModal
          message="Deseja realmente excluir este atendimento?"
          onConfirm={handleDelete}
          onCancel={() => setDeleteTarget(null)}
        />
      )}
    </div>
  )
}
