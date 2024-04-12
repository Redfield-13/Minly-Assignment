import * as React from 'react';
import { styled } from '@mui/material/styles';
import Button from '@mui/material/Button';
import CloudUploadIcon from '@mui/icons-material/CloudUpload';
import Grid from '@mui/material/Grid';
import useMutaion from '../hooks/useMutaion';
import { useState, useEffect, useContext, createContext, useRef } from 'react'
import UserContex from './Context'
import axios from 'axios';

const VisuallyHiddenInput = styled('input')({
  clip: 'rect(0 0 0 0)',
  clipPath: 'inset(50%)',
  height: 1,
  overflow: 'hidden',
  position: 'absolute',
  bottom: 0,
  left: 0,
  whiteSpace: 'nowrap',
  width: 1,
});

const URL = '/upload'




export default function UploadButton() {
  let user = useContext(UserContex);
  const {mutate:mute ,isLoading:uploadinf, error: uploadError} = useMutaion({url: URL})
  const apiUrl = 'https://k8fm9r7b-3456.uks1.devtunnels.ms/upload/upload?authorID='+user.id+'&author='+user.name
  const handleUpload = async (e)=>{
    const file = await e.target.files[0];
    if (file) {
      console.log(file);
      const form = new FormData()
      form.append('image', file)
      form.append('name', file.name)
      form.append('mimetype', file.type)
      const response = await axios.post(apiUrl, form, {
        headers: {
          'Content-Type': 'multipart/form-data', // Set content type
        },
      })
      console.log(response.data);
    }

  }
  const inputRef = useRef(null);



  const handleClick = () => {
    // inputRef.current.click();
  };

  return (
    <Button
      onClick={handleClick}
      sx={{ display:'grid', placeItems:'center'}}
      component="label"
      role={undefined}
      variant="contained"
      tabIndex={-1}
      startIcon={<CloudUploadIcon />}
    >
      Upload file
      <VisuallyHiddenInput type="file" ref={inputRef} onChange={handleUpload} />
    </Button>
  );
}