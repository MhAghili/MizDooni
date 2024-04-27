import React from "react";

export const SignUp = () => {
  return (
    <div className="col-12 p-4">
      <form className="cardRadius p-4">
        <div className="mb-3">
          <label for="username" className="form-label">
            Username
          </label>
          <input type="text" className="form-control bg-light" />
        </div>
        <div className="mb-3">
          <label for="password" className="form-label">
            Password
          </label>
          <input type="text" className="form-control bg-light" />
        </div>
        <div className="mb-3">
          <label for="country" className="form-label">
            Country
          </label>
          <input type="text" className="form-control bg-light" />
        </div>
        <div className="mb-3">
          <label for="city" className="form-label">
            City
          </label>
          <input type="text" className="form-control bg-light" />
        </div>

        <div className="mb-3 text-center">
          <button type="submit" className="btn btn-danger col-9 ">
            Sign Up
          </button>
        </div>
      </form>
    </div>
  );
};
