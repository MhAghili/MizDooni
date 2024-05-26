import React from "react";

export const Reservation = ({ restaurantAvailableTimes, handleTimeClick }) => {
  return (
    <>
      {restaurantAvailableTimes[0]?.availableTimes.map((item) => (
        <ReserveTime
          key={item}
          time={item}
          onClick={() => handleTimeClick(item)}
        />
      ))}
    </>
  );
};

export const ReserveTime = ({ time, onClick }) => {
  const [isSelected, setIsSelected] = React.useState(false);
  const handleClick = () => {
    setIsSelected(!isSelected);
    onClick(time); // Pass the time back to the parent component
  };
  const extractHour = (dateTimeString) => {
    const date = new Date(dateTimeString);
    const hour = date.getHours();
    return `${hour}:00`;
  };

  return (
    <div
      className={`cursor-pointer col-4 col-md-2 py-1 text-center border border-danger cardRadius m-1 d-flex align-items-center ${
        isSelected ? "bg-danger" : "bg-light"
      }`}
      onClick={handleClick}
      style={{ cursor: "pointer" }}
    >
      <span className={`text-${isSelected ? "light" : "danger"} m-auto`}>
        {extractHour(time)}
      </span>
    </div>
  );
};
