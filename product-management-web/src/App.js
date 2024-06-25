import React from 'react';
import {BrowserRouter, Route, Routes} from 'react-router-dom';

import './App.css';

import HeaderComponent from './components/HeaderComponent/HeaderComponent';
import AddProductComponent from './components/AddProductComponent/AddProductComponent';
import ProductListComponent from './components/ProductListComponent/ProductListComponent';
import LoginComponent from "./components/LoginComponent/LoginComponent";
import LogoutComponent from "./components/LoginComponent/LogoutComponent";

// Bootstrap CSS
import "bootstrap/dist/css/bootstrap.min.css";
// Bootstrap Bundle JS
import "bootstrap/dist/js/bootstrap.bundle.min";

function App() {
    return (
        <div className='App'>
            <BrowserRouter>
                <HeaderComponent/>
                <div className='container'>
                    <Routes>
                        <Route exact path='/login' element={<LoginComponent/>}></Route>
                        <Route exact path='/logout' element={<LogoutComponent/>}></Route>
                        <Route path='/' element={<ProductListComponent/>}></Route>
                        <Route path='/products' element={<ProductListComponent/>}></Route>
                        <Route path='/add-product' element={<AddProductComponent/>}></Route>
                        <Route path='/edit-product/:id' element={<AddProductComponent/>}></Route>
                    </Routes>
                </div>
            </BrowserRouter>
        </div>
    );
}

export default App;
