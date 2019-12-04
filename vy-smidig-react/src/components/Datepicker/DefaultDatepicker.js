import React, {Component} from 'react';
import 'react-dates/initialize';
import 'react-dates/lib/css/_datepicker.css';
import 'moment/locale/nb';

// Norwegian locale
import nbLocale from 'moment/locale/nb';
import moment from 'moment';

// date-react from airbnb
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
        moment.locale('nb', nbLocale);

        return (
            <div className="default-date-picker">
                <DateRangePicker
                    startDatePlaceholderText="Fra når"
                    endDatePlaceholderText="til når"
                    startDateId="travelDate"
                    endDateId="returnDate"
                    startDate={this.state.startDate}
                    endDate={this.state.endDate}
                    onDatesChange={({startDate, endDate}) => {
                        this.setState({startDate, endDate})
                        console.log("#travelDate: " + startDate);
                        console.log("#returnDate : " + endDate);
                    }}
                    focusedInput={this.state.focusedInput}
                    onFocusChange={(focusedInput) => {this.setState({focusedInput})}}
                />
            </div>
        );
    }
}

export default DefaultDatePicker;
