import React from "react";
import "../App.css";
import "../classes.css";
import ResPic from "../Statics/Res.jpg";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";

export const RestaurantCard = (props) => {
  return (
    <div className="col-auto border cardRadius m-3">
      <div className="container-fluid p-0">
        <div className="row p-0 mb-2">
          <a href="/" className="p-0 position-relative text-warning">
            <img
              src={ResPic}
              className="restaurantPic p-0 imgcustomBorderRadius"
              alt=""
            />
            <div className="position-absolute bg-light col-6 radiusEnd badgeTop">
              <div className="row">
                <div className="col">
                  <FontAwesomeIcon icon={faStar} className="fa-sm fa" />
                  <FontAwesomeIcon icon={faStar} className="fa-sm fa" />
                  <FontAwesomeIcon icon={faStar} className="fa-sm fa" />
                  <FontAwesomeIcon icon={faStar} className="fa-sm fa" />
                  <FontAwesomeIcon icon={faStar} className="fa-sm fa" />
                </div>
              </div>
            </div>
          </a>
        </div>
        <div className="row ms-1 fs-14 darkGray">Elina cofee</div>
        <div className="row ms-1 gray fs-10">238 Reviews</div>
        <div className="row ms-1 fs-10">Fast Food</div>
        <div className="row ms-1">
          <div className="col-12 p-0">
            <i className="fa-solid fa-location-dot"></i>
            <span className="ms-1 darkGray fs-10">Tehran</span>
          </div>
        </div>
        <div className="row ms-1 mb-1 align-items-center">
          <div className="col-2 p-0">
            <span className="text-success fs-10">open</span>
          </div>
          <div className="col-10 p-0">
            <span className="fs-10">Closed At 11PM</span>
          </div>
        </div>
      </div>
    </div>
  );
};
