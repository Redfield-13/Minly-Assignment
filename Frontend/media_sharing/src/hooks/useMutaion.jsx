import React , {useState}from 'react'




import axios from 'axios';

const baseURL = 'http://localhost:3456/upload';

const axiosClient = axios.create({
  baseURL,
  headers: {
  },
});

function useMutaion({url, method = "POST"}) {
    const [state,setstate] = useState({
        isLoading: false,
        error:''
    })
    const mute = async (fileName) =>{
        setstate( prev => ({
            ...prev,
            isLoading:true,
        }))

        axiosClient({url, method, fileName}).then(()=>{
            setstate({isLoading:false, error:''})
        }).catch((error)=>{
            setstate({isLoading: false, error:error})
        })

    }
  return {mutate:mute, ...state}
}

export default useMutaion