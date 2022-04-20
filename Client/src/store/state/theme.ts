import { atom } from "recoil"

// 1번 타입을 먼저 등록을 해준다 . 🎨
interface Itheme {
  bgColor: string
  fontColor: string
  navColor: string
  activeBtnColor: string
  inactiveBtnColor: string
  containerColor: string
  listBgColor: string[]
}
// 2번 색을 지정해준다 => main 색들 넣어주고 다른곳에서 
// color : ${props => props.theme.fontColor}; 이런식으로 사용하기
export const DARKMODE = {
  bgColor: '#1D262B',
  fontColor: 'white',
  navColor: '#275788',
  activeBtnColor: '#005AB5',
  inactiveBtnColor: '#CCCCCC',
  containerColor: '#151B20',
  listBgColor: ['#0E81F7', '#3B9CFF', '#005BB7'],
}
export const LIGHTMODE = {
  bgColor: 'white',
  fontColor: '#121212',
  navColor: '#E9F2FF',
  activeBtnColor: '#96BDF3',
  inactiveBtnColor: '#CCCCCC',
  containerColor: '#F8F8F8',
  listBgColor: ['#E9F2FF', '#C1D9FB', '#C3DCFF'],
}

export const themeState = atom<Itheme>({
  key: 'theme',
  default: LIGHTMODE
})


