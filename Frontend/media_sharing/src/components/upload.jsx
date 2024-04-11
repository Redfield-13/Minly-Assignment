import React from 'react'
import MenuAppBar from './bar'
import Grid from '@mui/material/Grid';
import UploadButton from './uploadButton'
import '../App.css'

function Upload() {
  return (
    <Grid container direction="column" spacing={2}>
        <Grid item xs={6} alignItems="center"> <MenuAppBar></MenuAppBar> </Grid>
        <Grid item alignContent="center"> <UploadButton ></UploadButton> </Grid>

    </Grid>
  )
}

export default Upload