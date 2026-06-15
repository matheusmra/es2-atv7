import api from './axios'

export const getProfissionais = () => api.get('/profissionais')
export const getProfissionalById = (id) => api.get(`/profissionais/${id}`)
export const getProfissionaisByNome = (nome) => api.get('/profissionais', { params: { nome } })
export const getProfissionaisByCategoria = (categoria) => api.get('/profissionais', { params: { categoria } })
export const createProfissional = (data) => api.post('/profissionais', data)
export const updateProfissional = (id, data) => api.put(`/profissionais/${id}`, data)
export const deleteProfissional = (id) => api.delete(`/profissionais/${id}`)
