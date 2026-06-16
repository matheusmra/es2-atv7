import { useEffect, useState } from 'react'
import {
  getProfissionais,
  getProfissionaisByNome,
  getProfissionaisByCategoria,
  deleteProfissional,
} from '../../api/profissionais'
import Button from '../../components/ui/Button'
import Input from '../../components/ui/Input'
import Select from '../../components/ui/Select'
import ConfirmModal from '../../components/ConfirmModal'
import './Profissionais.css'

const CATEGORIAS_OPTIONS = [
  { value: '', label: 'Todas as categorias' },
  { value: 'MEDICO', label: 'Médico' },
  { value: 'FISIOTERAPEUTA', label: 'Fisioterapeuta' },
  { value: 'PSICOLOGO', label: 'Psicólogo' },
]

const CATEGORIA_LABEL = {
  MEDICO: 'Médico',
  FISIOTERAPEUTA: 'Fisioterapeuta',
  PSICOLOGO: 'Psicólogo',
}

const CATEGORIA_CLASS = {
  MEDICO: 'badge badge-medico',
  FISIOTERAPEUTA: 'badge badge-fisio',
  PSICOLOGO: 'badge badge-psico',
}

export default function ProfissionaisList() {
  const [profissionais, setProfissionais] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [searchNome, setSearchNome] = useState('')
  const [searchId, setSearchId] = useState('')
  const [filterCategoria, setFilterCategoria] = useState('')
  const [deleteTarget, setDeleteTarget] = useState(null)

  const fetchAll = async () => {
    try {
      setLoading(true)
      const res = await getProfissionais()
      setProfissionais(res.data)
    } catch {
      setError('Erro ao carregar profissionais.')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { fetchAll() }, [])

  const handleSearch = async () => {
    try {
      setLoading(true)
      let res
      if (searchId.trim()) {
        const { getProfissionalById } = await import('../../api/profissionais')
        res = await getProfissionalById(searchId.trim())
        setProfissionais(res.data ? [res.data] : [])
        return
      } else if (filterCategoria) {
        res = await getProfissionaisByCategoria(filterCategoria)
      } else if (searchNome.trim()) {
        res = await getProfissionaisByNome(searchNome.trim())
      } else {
        res = await getProfissionais()
      }
      setProfissionais(res.data)
    } catch {
      setError('Erro ao buscar profissionais.')
    } finally {
      setLoading(false)
    }
  }

  const handleDelete = async () => {
    try {
      await deleteProfissional(deleteTarget)
      setDeleteTarget(null)
      fetchAll()
    } catch {
      setError('Erro ao excluir profissional.')
      setDeleteTarget(null)
    }
  }

  return (
    <div className="page">
      <div className="page-header">
        <h1 className="page-title">Profissionais de Saúde</h1>
        <Button to="/profissionais/novo">
          <span className="material-symbols-outlined">add</span>
          Novo Profissional
        </Button>
      </div>

      <div className="filters">
        <Input
          icon="tag"
          placeholder="Buscar por ID..."
          value={searchId}
          onChange={(e) => { setSearchId(e.target.value); setSearchNome(''); setFilterCategoria('') }}
        />
        <Input
          icon="search"
          placeholder="Buscar por nome..."
          value={searchNome}
          onChange={(e) => { setSearchNome(e.target.value); setSearchId(''); setFilterCategoria('') }}
        />
        <Select
          value={filterCategoria}
          onChange={(e) => { setFilterCategoria(e.target.value); setSearchNome('') }}
          options={CATEGORIAS_OPTIONS}
        />
        <Button variant="secondary" onClick={handleSearch}>
          <span className="material-symbols-outlined">search</span>
          Buscar
        </Button>
        <Button variant="ghost" onClick={() => { setSearchNome(''); setSearchId(''); setFilterCategoria(''); fetchAll() }}>
          Limpar
        </Button>
      </div>

      {error && <div className="alert alert-error">{error}</div>}

      {loading ? (
        <div className="spinner-wrap"><div className="spinner" /></div>
      ) : profissionais.length === 0 ? (
        <div className="empty-state">Nenhum profissional encontrado.</div>
      ) : (
        <div className="table-wrap">
          <table className="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Telefone</th>
                <th>Endereço</th>
                <th>Categoria</th>
                <th>Ações</th>
              </tr>
            </thead>
            <tbody>
              {profissionais.map((p) => (
                <tr key={p.id}>
                  <td className="id-col">{p.id}</td>
                  <td className="bold">{p.nome}</td>
                  <td>{p.telefone}</td>
                  <td>{p.endereco}</td>
                  <td><span className={CATEGORIA_CLASS[p.categoria]}>{CATEGORIA_LABEL[p.categoria]}</span></td>
                  <td>
                    <div className="action-btns">
                      <Button to={`/profissionais/${p.id}/editar`} variant="secondary" size="sm">
                        <span className="material-symbols-outlined">edit</span>
                        Editar
                      </Button>
                      <Button variant="danger" size="sm" onClick={() => setDeleteTarget(p.id)}>
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
          message="Deseja realmente excluir este profissional?"
          onConfirm={handleDelete}
          onCancel={() => setDeleteTarget(null)}
        />
      )}
    </div>
  )
}
