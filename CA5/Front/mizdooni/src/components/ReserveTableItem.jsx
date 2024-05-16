import React from "react";

export const ReserveTableItem = (props) => {
  const extractHour = (dateTimeString) => {
    const date = new Date(dateTimeString);
    const hour = date.getHours();
    return `${hour}:00`;
  };
  return (
    <tr className="">
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
