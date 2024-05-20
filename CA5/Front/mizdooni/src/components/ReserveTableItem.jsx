import React from "react";

export const ReserveTableItem = (props) => {
  const extractHour = (dateTimeString) => {
    const date = new Date(dateTimeString);
    const hour = date.getHours();
    return `${hour}:00`;
  };
  const isPastReservation = (dateTimeString) => {
    const reservationDate = new Date(dateTimeString);
    return reservationDate < new Date();
  };
  return (
    <tr
      className={
        isPastReservation(props.date) ? "text-decoration-line-through" : ""
      }
    >
      <td>
        At: <span className="text-danger">{extractHour(props.date)}</span>
      </td>
      <td>By {props.name}</td>
      <td>
        Table - <span className="text-success">{props.tableNumber}</span>
      </td>
    </tr>
  );
};
