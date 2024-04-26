import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "../classes.css";
import SharpSign from "../Statics/SharpSign.png";
import Sofa from "../Statics/Sofa.png";
export const ReserveCard = () => {
  return (
    <div className="col-md-auto darkRed border cardRadius m-3">
      <div className="container-fluid p-2">
        <div className="row ms-1 mb-1 align-items-center">
          <div className="col-auto">
            <img src={SharpSign} alt="" />
          </div>
          <div className="col-auto text-light">1</div>
        </div>
        <div className="row ms-1 mb-1 align-items-center">
          <div className="col-auto">
            <img src={Sofa} alt="" />
          </div>
          <div className="col-auto text-light">4</div>
        </div>
      </div>
    </div>
  );
};
