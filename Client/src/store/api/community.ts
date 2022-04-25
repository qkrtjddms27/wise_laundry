import axios from "axios"

const baseURL = process.env.REACT_APP_BASEURL
const token = sessionStorage.getItem('jwt')

const apiClient = axios.create({
  baseURL: baseURL,
  headers: {
    "Content-type": "application/json",
    'Authorization':`Bearer ${token}`
  },
}); 

export const getCommunityAll = async () => {
  const response = await apiClient.get<any>(
    '/community/all', 
  )
  console.log('getCommunityAll response: ', response)
  return response.data
}

export const getCommunityDetail = async (boardId: number) => {
  const response = await apiClient.get<any>(
    `/community/${boardId}`,
    )
  console.log('getCommunityDetail response: ', response)
  return response.data
}

export const delBoard = async (boardId: number) => {
  const response = await apiClient.delete<any>(
    `/community/${boardId}`,
    )
  console.log('delBoard response: ', response)
  return response
}

export const postComment = async (data: any) => {
  const response = await apiClient.post<any>(
    '/community/comment/create',
    data
  )
  console.log('postComment response: ', response)
  return response.data
}

export const delComment = async (commentId: number) => {
  const response = await apiClient.delete<any>(
    `/community/comment/${commentId}`,
    )
  console.log('delComment response: ', response)
  return response
}

