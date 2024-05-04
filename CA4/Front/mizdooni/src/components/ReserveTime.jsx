import React from 'react';

export const Reservation = ({ restaurantAvailableTimes, handleTimeClick }) => {
  return (
    <>
      {restaurantAvailableTimes.map((item) => (
        <ReserveTime
          key={item.time}
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

  return (
    <div
      className={`cursor-pointer col-4 col-md-2 py-1 text-center border border-danger cardRadius m-1 d-flex align-items-center ${
        isSelected ? 'bg-danger' : 'bg-light'
      }`}
      onClick={handleClick}
    >
      <span className={`text-${isSelected ? 'light' : 'danger'} m-auto`}>
        {time} : 00
      </span>
    </div>
  );
};
