import * as React from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { Button, CardActionArea, CardActions } from '@mui/material';
import Heart from "react-animated-heart";
import DeleteIcon from '@mui/icons-material/Delete';
import { toast } from 'react-toastify';
import axios from 'axios';
import { useState, useContext, useEffect } from 'react';
import UserContex from './Context'
import { Link } from 'react-router-dom';


export default function MediaCover(props) {

  let user = useContext(UserContex);
  const [isClick, setClick] = useState(JSON.parse(localStorage.getItem("clicked")));
  localStorage.setItem("clicked", JSON.stringify(isClick))
  let [liked,setLiked] = useState(false)
  let [unLiked,setUnLiked] = useState(false)
  const [likes, setLikes] = useState(props.likes);
  const apiUrl = 'https://k8fm9r7b-3456.uks1.devtunnels.ms/likes?liker='+user.id +'&currentlikes='+likes+'&mediaID='+props.id+'&operation='
  let delUrl = 'https://k8fm9r7b-3456.uks1.devtunnels.ms/delete?imgID='
  const handleLike = async () => {
    console.log(JSON.parse(localStorage.getItem("clicked")));
    if (JSON.parse(localStorage.getItem("clicked"))) {
      setLikes(likes-1);
        setLiked(!JSON.parse(localStorage.getItem("clicked")))
        setClick(!JSON.parse(localStorage.getItem("clicked")))
      try {
        const response = await axios.post(apiUrl+'unlike', {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        
        
      } catch (error) {
        console.error(error);
      }
    }else{
      if (liked) {
       
    }else{
         try {
            const response = await axios.post(apiUrl+'like', {
              headers: {
                'Content-Type': 'application/json'
              }
            });
            setLikes(likes + 1);
            setLiked(!JSON.parse(localStorage.getItem("clicked")))
            setClick(!JSON.parse(localStorage.getItem("clicked")))
            
          } catch (error) {
            console.error(error);
          }
    }
    }
    
      
  };
  const handleunLike = async () => {
      if (unLiked) {
          setLikes(likes + 1)
          setUnLiked(false)
      }
      else{
        try {
          const response = await axios.post(apiUrl+'unlike', {
            headers: {
              'Content-Type': 'application/json'
            }
          });

          setLikes(likes - 1);
          setUnLiked(true)
        } catch (error) {
          console.error(error);
        }
      }
      
    };

    const handleDelete = async ()=>{
      try{
        const response = await axios.post(delUrl+props.id,{
          headers: {
            'Content-Type': 'application/json'
          }
        })
        console.log(response);
        toast.info('Media Was Deleted Successfully. Refresh The Page')
      }catch(error){
        console.log(error);
      }
    }

  return (
    <Box
      component="ul"
      sx={{ display: 'flex', gap: 2, flexWrap: 'wrap', p: 0, m: 0 }}
    >
      <Card  component="li" sx={{  boxShadow:10,  margin:'auto', marginTop:5 ,maxWidth: 345, flexGrow: 1 }}>
          <video  
            loop       
            controls
            width={345}
            poster={props.media}
          >
            <source
              src={props.media}
              type="video/mp4"
            />
          </video>
          <Link to={`/profile/${props.authorId}`} style={{ textDecoration: 'none', color: 'inherit', margin:'auto' }}>
            <CardContent sx={{marginLeft:4}}>
              <Typography onClick={() => {console.log(props.authorId);}} gutterBottom variant="h5" component="div">
                Posted By: {props.author}
              </Typography>
            </CardContent>
        </Link>
        <CardActions sx={{marginLeft:10}}>
        <Button size="small" color="primary">
        <Heart  isClick={JSON.parse(localStorage.getItem("clicked"))} onClick={handleLike} />
          <Typography sx={{marginRight:1, fontSize:27, marginTop:0.25, color:'#e2264d'}}>{likes}</Typography>
        </Button>
      </CardActions>
      {props.uploadpage == true && (
              <Button sx={{marginLeft:17.2}} size="small" color="error">
                <DeleteIcon onClick={handleDelete}></DeleteIcon>
              </Button>
            )}
      </Card>
    </Box>
  );
}