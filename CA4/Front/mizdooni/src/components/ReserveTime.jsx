import React, { useState } from 'react';

export const Reservation = ({ restaurantAvailableTimes }) => {
  const [selectedTime, setSelectedTime] = useState(null);

  const handleTimeClick = (time) => {
    setSelectedTime(time);
  };

  return (
    <div className="row">
      {restaurantAvailableTimes.map((item) => (
        <ReserveTime
          key={item.time}
          time={item}
          isSelected={selectedTime === item}
          onClick={() => handleTimeClick(item)}
        />
      ))}
    </div>
  );
};

export const ReserveTime = ({ time, isSelected, onClick }) => {
  return (
    <div
      className={`col-4 col-md-2 py-1 text-center border border-danger cardRadius m-1 d-flex align-items-center ${
        isSelected ? 'bg-danger' : 'bg-light'
      }`}
      onClick={onClick}
    >
      <span className={`text-${isSelected ? 'light' : 'danger'} m-auto`}>
        {time} : 00
      </span>
    </div>
  );
};
