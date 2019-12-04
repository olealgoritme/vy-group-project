import React, {Component} from 'react';
import 'react-dates/initialize';
import 'react-dates/lib/css/_datepicker.css';


import {DateRangePicker} from 'react-dates';

class DefaultDatePicker extends Component {
    constructor(props) {
        super(props);
        this.state = {
            startDate: null,
            endDate: null,
            focusedInput: null,
        };
    }

    render() {
        return (
            <div className="default-date-picker">
                <DateRangePicker
                    startDateId="startDate"
                    endDateId="endDate"
                    startDate={this.state.startDate}
                    endDate={this.state.endDate}
                    onDatesChange={({startDate, endDate}) => {
                        this.setState({startDate, endDate})
                        console.log("startDate: " + startDate);
                        console.log("endDate: " + endDate);
                    }}
                    focusedInput={this.state.focusedInput}
                    onFocusChange={(focusedInput) => {this.setState({focusedInput})}}
                />
            </div>
        );
    }
}

export default DefaultDatePicker;
