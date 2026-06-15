import api from './axios'

export const getExames = () => api.get('/exames')
export const getExameById = (id) => api.get(`/exames/${id}`)
export const createExame = (data) => api.post('/exames', data)
export const updateExame = (id, data) => api.put(`/exames/${id}`, data)
export const deleteExame = (id) => api.delete(`/exames/${id}`)
