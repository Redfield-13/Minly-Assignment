import * as React from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { Button, CardActionArea, CardActions } from '@mui/material';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';

export default function MediaCover(props) {
  return (
    <Box
      component="ul"
      sx={{ display: 'flex', gap: 2, flexWrap: 'wrap', p: 0, m: 0 }}
    >
      <Card component="li" sx={{ boxShadow:10,  margin:'auto', marginTop:5 ,maxWidth: 345, flexGrow: 1 }}>
          <video
            autoPlay
            loop
            muted
            poster={props.media}
          >
            <source
              src={props.media}
              type="video/mp4"
            />
          </video>
          <CardContent sx={{marginLeft:4}}>
          <Typography gutterBottom variant="h5" component="div">
             Posted By : {props.author}
          </Typography>
        </CardContent>
        <CardActions sx={{marginLeft:15}}>
        <Button size="small" color="primary">
          <FavoriteBorderIcon></FavoriteBorderIcon>
        </Button>
      </CardActions>
      </Card>
    </Box>
  );
}