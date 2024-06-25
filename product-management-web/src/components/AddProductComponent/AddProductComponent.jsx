import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from 'react-router-dom';
import './AddProductComponent.css';
import PopUpComponent from '../PopUpComponent/PopUpComponent';
import ProductService from '../../services/ProductService';

const AddProductComponent = () => {
    let navigate = useNavigate();

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [price, setPrice] = useState('');
    const [popupMessage, setPopupMessage] = useState('');
    const [showPopup, setShowPopup] = useState(false);
    const {id} = useParams();

    const pageTitle = id ? "Update Product" : "Create Product";
    const buttonLabel = id ? "Update Product" : "Create Product";

    const saveOrUpdateProduct = async (e) => {
        e.preventDefault();

        if (!title || !price) {
            setPopupMessage('Fill in the title and price of the product.');
            setShowPopup(true);
            return;
        }

        const product = {title, description, price};

        if (id) {
            ProductService.updateProduct(id, product).then((response) => {
                setPopupMessage('Successfully updated product');
                setShowPopup(true);
                console.log(response.data);
                setTimeout(()=>{
                    navigate("/products");
                },1000);
            }).catch(error => {
                console.log(error);
            });
        } else {
            ProductService.createProduct(product).then((response) => {
                setPopupMessage('Successfully created product');
                setShowPopup(true);
                console.log(response.data);
                setTimeout(()=>{
                    navigate("/products");
                },1000);
            }).catch(error => {
                console.log(error);
            });
        }
    };


    useEffect(() => {
        if (!id) {
            setTitle('');
            setDescription('');
            setPrice('');
        }
        ProductService.getProductById(id).then((response) => {
            setTitle(response.data.title);
            setDescription(response.data.description);
            setPrice(response.data.price);
        }).catch(error => {
            console.log(error);
        })
    }, [id]);

    return (
        <div className='add-product'>
            <div className="add-product-form">
                <h2>{pageTitle}</h2>
                <form>
                    <div className="form-group">
                        <input type="text" id="title" name="title" placeholder="title" value={title}
                               onChange={(e) => setTitle(e.target.value)} required/>
                    </div>
                    <div className="form-group">
                        <input type="number" id="price" name="price" placeholder="price" value={price}
                               onChange={(e) => setPrice(e.target.value)} required/>
                    </div>
                    <div className="form-group">
                        <textarea id="description" name="description" rows="4" cols="50" placeholder="description"
                                  value={description} onChange={(e) => setDescription(e.target.value)}/>
                    </div>
                    <div className='form-group-button'>
                        <button type="submit" onClick={(e) => saveOrUpdateProduct(e)}>
                            {buttonLabel}
                        </button>
                    </div>
                </form>
            </div>
            <PopUpComponent message={popupMessage} show={showPopup} onClose={() => setShowPopup(false)}/>
        </div>
    );
};

export default AddProductComponent;
