import React from "react";

export const ReserveTableItem = (props) => {
  const extractHour = (dateTimeString) => {
    const date = new Date(dateTimeString);
    const hour = date.getHours();
    return `${hour}: 00`;
  };
  return (
    <tr className="">
      <td>At: {extractHour(props.date)}</td>
      <td>By {props.name}</td>
      <td>Table-{props.tableNumber}</td>
    </tr>
  );
};
