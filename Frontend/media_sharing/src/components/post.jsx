import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { Button, CardActionArea, CardActions } from '@mui/material';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import { red } from '@mui/material/colors';

export default function MultiActionAreaCard(props) {
  return (
    <Card sx={{ maxWidth: 345, margin:'auto', marginTop: 5, boxShadow:10}}>
      <CardActionArea>
        <CardMedia
          component='img'
          height="250"
          image={props.image}
          alt="green iguana"
        />
        <CardContent sx={{marginLeft:4}}>
          <Typography gutterBottom variant="h5" component="div">
             Posted By : {props.author}
          </Typography>
        </CardContent>
      </CardActionArea>
      <CardActions sx={{marginLeft:15}}>
        <Button size="small" color="primary">
          <FavoriteBorderIcon color='' sx={{backgroundColor:red}}></FavoriteBorderIcon>
        </Button>
      </CardActions>
    </Card>
  );
}