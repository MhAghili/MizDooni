import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";

export const Review = (props) => {
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
  return (
    <div className="row d-flex pb-3 mb-3 border-bottom">
      <div className="col-auto">
        <div className="d-flex justify-content-center align-items-center text-bold circle pink">
          AD
        </div>
      </div>
      <div className="col-7">
        <div className="row text-bold fs-18">{props.name}</div>
        <div className="row fs-10 d-flex justify-content-start">
          <div className="col-auto">
            Overall <span className="text-danger">{props.scores.overall}</span>
          </div>
          <div className="col-auto">
            Food <span className="text-danger">{props.scores.food}</span>
          </div>
          <div className="col-auto">
            Service <span className="text-danger">{props.scores.service}</span>
          </div>
          <div className="col-auto">
            Ambience{" "}
            <span className="text-danger">{props.scores.ambience}</span>
          </div>
        </div>
        <div className="row fs-14">{props.comment}</div>
      </div>
      <div className="col-4 text-end text-warning ">
        {calculateStarRating(props.scores.overall)}
        <span className="gray fs-14 ms-2">Dined on February 17, 2024</span>
      </div>
    </div>
  );
};
