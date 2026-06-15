import api from './axios'

export const getAtendimentos = () => api.get('/atendimentos')
export const getAtendimentoById = (id) => api.get(`/atendimentos/${id}`)
export const createAtendimento = (data) => api.post('/atendimentos', data)
export const updateAtendimento = (id, data) => api.put(`/atendimentos/${id}`, data)
export const deleteAtendimento = (id) => api.delete(`/atendimentos/${id}`)
