import React from "react";

export const ReviewItem = (props) => {
  return (
    <div className="col-2 d-flex flex-column align-items-center">
      <div className="row pb-2 mb-2">{props.name}</div>
      <div className="row">{props.rate}</div>
    </div>
  );
};
