import React from "react";
import "../App.css";
import "../classes.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar, faLocationDot } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";

export const RestaurantCard = (props) => {
  const score = 3;
  const navigate = useNavigate();
  const calculateStarRating = (score) => {
    const fullStars = Math.floor(score);
    const stars = [];
    for (let i = 0; i < fullStars; i++) {
      stars.push(
        <FontAwesomeIcon icon={faStar} className="fa-sm fa" key={i} />
      );
    }

    return stars;
  };

  const clickHndlr = () => {
    navigate("/Restaurant", {
      state: { restaurantName: props.name },
    });
  };

  const extractHourMinute = (dateTimeString) => {
    const date = new Date(dateTimeString);
    const hour = date.getHours();
    const minute = date.getMinutes();
    return `${hour}:${minute}`;
  };

  return (
    <div className="col-2 border cardRadius m-3">
      <div className="container-fluid p-0">
        <div className="row p-0 mb-2">
          <a
            onClick={() => clickHndlr()}
            className="p-0 position-relative text-warning"
            style={{ cursor: "pointer" }}
          >
            <img
              src={props.image}
              className="restaurantPic p-0 imgcustomBorderRadius"
              alt=""
            />
            <div className="position-absolute bg-light col-4 pe-2 radiusEnd badgeTop">
              <div className="row">
                <div className="col">{calculateStarRating(score)}</div>
              </div>
            </div>
          </a>
        </div>
        <div className="row ms-1 fs-14 darkGray">{props.name}</div>
        <div className="row ms-1 gray fs-10">238 Reviews</div>
        <div className="row ms-1 fs-10">{props.type}</div>
        <div className="row ms-1">
          <div className="col-12 p-0">
            <FontAwesomeIcon icon={faLocationDot} className="fa-xs fa" />
            <span className="ms-1 darkGray fs-10">{props.address.city}</span>
          </div>
        </div>
        <div className="row ms-1 mb-1 align-items-center">
          <div className="col-2 p-0">
            <span className="text-success fs-10">open</span>
          </div>
          <div className="col-10 p-0">
            <span className="fs-10">
              Closed At{" "}
              <span className="text-danger">
                {extractHourMinute(props.endTime)}
              </span>{" "}
              PM
            </span>
          </div>
        </div>
      </div>
    </div>
  );
};
