import React, { createContext } from 'react';

const UserContext = createContext({
    
        user:{avatar: '',
        id: 0,
        name:''},
        setUser: () =>{}
});

export default UserContext;