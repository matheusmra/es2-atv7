import { useEffect, useState } from 'react'
import { getExames, deleteExame } from '../../api/exames'
import Button from '../../components/ui/Button'
import Input from '../../components/ui/Input'
import ConfirmModal from '../../components/ConfirmModal'

export default function ExamesList() {
  const [exames, setExames] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [searchId, setSearchId] = useState('')
  const [deleteTarget, setDeleteTarget] = useState(null)

  const fetchAll = async () => {
    try {
      setLoading(true)
      const res = await getExames()
      setExames(res.data)
    } catch {
      setError('Erro ao carregar exames.')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { fetchAll() }, [])

  const handleSearch = async () => {
    try {
      setLoading(true)
      if (searchId.trim()) {
        const { getExameById } = await import('../../api/exames')
        const res = await getExameById(searchId.trim())
        setExames(res.data ? [res.data] : [])
      } else {
        const res = await getExames()
        setExames(res.data)
      }
    } catch {
      setError('Erro ao buscar exames.')
    } finally {
      setLoading(false)
    }
  }

  const handleDelete = async () => {
    try {
      await deleteExame(deleteTarget)
      setDeleteTarget(null)
      fetchAll()
    } catch {
      setError('Erro ao excluir exame.')
      setDeleteTarget(null)
    }
  }

  return (
    <div className="page">
      <div className="page-header">
        <h1 className="page-title">Exames Laboratoriais</h1>
        <Button to="/exames/novo">
          <span className="material-symbols-outlined">add</span>
          Novo Exame
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
      ) : exames.length === 0 ? (
        <div className="empty-state">Nenhum exame cadastrado.</div>
      ) : (
        <div className="table-wrap">
          <table className="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Descrição</th>
                <th>Atendimento</th>
                <th>Ações</th>
              </tr>
            </thead>
            <tbody>
              {exames.map((e) => (
                <tr key={e.id}>
                  <td className="id-col">{e.id}</td>
                  <td>{e.descricao}</td>
                  <td>{e.atendimentoId ? `#${e.atendimentoId}` : '—'}</td>
                  <td>
                    <div className="action-btns">
                      <Button to={`/exames/${e.id}/editar`} variant="secondary" size="sm">
                        <span className="material-symbols-outlined">edit</span>
                        Editar
                      </Button>
                      <Button variant="danger" size="sm" onClick={() => setDeleteTarget(e.id)}>
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
          message="Deseja realmente excluir este exame?"
          onConfirm={handleDelete}
          onCancel={() => setDeleteTarget(null)}
        />
      )}
    </div>
  )
}
