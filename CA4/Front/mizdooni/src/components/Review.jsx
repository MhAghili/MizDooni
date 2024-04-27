import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";

export const Review = () => {
  return (
    <div className="row d-flex pb-3 mb-3 border-bottom">
      <div className="col-auto">
        <div className="d-flex justify-content-center align-items-center text-bold circle pink">
          AD
        </div>
      </div>
      <div className="col-7">
        <div className="row text-bold fs-18">Ali Daei</div>
        <div className="row fs-10 d-flex justify-content-start">
          <div className="col-auto">
            Overall <span className="text-danger">5</span>
          </div>
          <div className="col-auto">
            Food <span className="text-danger">5</span>
          </div>
          <div className="col-auto">
            Service <span className="text-danger">5</span>
          </div>
          <div className="col-auto">
            Ambience <span className="text-danger">5</span>
          </div>
        </div>
        <div className="row fs-14">
          Excellent pre-theatre meal. Good food and service. Only small
          criticism is that music was intrusive.
        </div>
      </div>
      <div className="col-4 text-end text-warning">
        <FontAwesomeIcon icon={faStar} className="fa-sm fa" />
        <FontAwesomeIcon icon={faStar} className="fa-sm fa" />
        <FontAwesomeIcon icon={faStar} className="fa-sm fa" />
        <FontAwesomeIcon icon={faStar} className="fa-sm fa" />
        <span className="gray fs-14">Dined on February 17, 2024</span>
      </div>
    </div>
  );
};
