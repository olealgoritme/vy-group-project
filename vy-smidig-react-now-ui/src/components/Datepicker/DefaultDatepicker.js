/*eslint-disable*/
import React from "react";

// reactstrap components
import {Container} from "reactstrap";
// core components

function DefaultDatePicker() {
    let pageHeader = React.createRef();

    React.useEffect(() => {
        if (window.innerWidth > 991) {
            const updateScroll = () => {
                let windowScrollTop = window.pageYOffset / 3;
                pageHeader.current.style.transform =
                    "translate3d(0," + windowScrollTop + "px,0)";
            };
            window.addEventListener("scroll", updateScroll);
            return function cleanup() {
                window.removeEventListener("scroll", updateScroll);
            };
        }
    });

    return (
        <DateRangePickerWrapper
            anchorDirection="left"
            autoFocus={false}
            autoFocusEndDate={false}
            block={false}
            customArrowIcon={null}
            customCloseIcon={null}
            customInputIcon={null}
            disabled={false}
            displayFormat={function noRefCheck() {}}
            enableOutsideDays={false}
            endDateId="endDate"
            endDatePlaceholderText="End Date"
            horizontalMargin={0}
            initialEndDate={Fri Dec 13 2019 14: 28: 57 GMT + 0100
}
initialStartDate = {Fri Dec 06 2019 14: 28: 57 GMT + 0100}
initialVisibleMonth = {null}
isDayBlocked = {function noRefCheck() {}}
isDayHighlighted = {function noRefCheck() {}}
isOutsideRange = {function noRefCheck() {}}
isRTL = {false}
keepOpenOnDateSelect = {false}
minimumNights = {1}
monthFormat = "MMMM YYYY"
navNext = {null}
navPosition = "navPositionTop"
navPrev = {null}
numberOfMonths = {2}
onClose = {function noRefCheck() {}}
onNextMonthClick = {function noRefCheck() {}}
onPrevMonthClick = {function noRefCheck() {}}
orientation = "horizontal"
phrases = {{
    calendarLabel: 'Calendar',
        chooseAvailableEndDate: function noRefCheck() {},
    chooseAvailableStartDate: function noRefCheck() {},
    clearDates: 'Clear Dates',
        closeDatePicker: 'Close',
            dateIsSelected: function noRefCheck() {},
    dateIsSelectedAsEndDate: function noRefCheck() {},
    dateIsSelectedAsStartDate: function noRefCheck() {},
    dateIsUnavailable: function noRefCheck() {},
    enterKey: 'Enter key',
        escape: 'Escape key',
            focusStartDate: 'Interact with the calendar and add the check-in date for your trip.',
                hideKeyboardShortcutsPanel: 'Close the shortcuts panel.',
                    homeEnd: 'Home and end keys',
                        jumpToNextMonth: 'Move forward to switch to the next month.',
                            jumpToPrevMonth: 'Move backward to switch to the previous month.',
                                keyboardBackwardNavigationInstructions: 'Navigate backward to interact with the calendar and select a date. Press the question mark key to get the keyboard shortcuts for changing dates.',
                                    keyboardForwardNavigationInstructions: 'Navigate forward to interact with the calendar and select a date. Press the question mark key to get the keyboard shortcuts for changing dates.',
                                        keyboardShortcuts: 'Keyboard Shortcuts',
                                            leftArrowRightArrow: 'Right and left arrow keys',
                                                moveFocusByOneDay: 'Move backward (left) and forward (right) by one day.',
                                                    moveFocusByOneMonth: 'Switch months.',
                                                        moveFocusByOneWeek: 'Move backward (up) and forward (down) by one week.',
                                                            moveFocustoStartAndEndOfWeek: 'Go to the first or last day of a week.',
                                                                openThisPanel: 'Open this panel.',
                                                                    pageUpPageDown: 'page up and page down keys',
                                                                        questionMark: 'Question mark',
                                                                            returnFocusToInput: 'Return to the date input field.',
                                                                                roleDescription: 'datepicker',
                                                                                    selectFocusedDate: 'Select the date in focus.',
                                                                                        showKeyboardShortcutsPanel: 'Open the keyboard shortcuts panel.',
                                                                                            upArrowDownArrow: 'up and down arrow keys'
}}
regular = {false}
renderCalendarDay = {undefined}
renderDayContents = {null}
renderMonthText = {null}
reopenPickerOnClearDates = {false}
required = {false}
screenReaderInputMessage = ""
showClearDates = {false}
showDefaultInputIcon
small = {false}
startDateId = "startDate"
startDatePlaceholderText = "Start Date"
stateDateWrapper = {function noRefCheck() {}}
withFullScreenPortal = {false}
withPortal = {false}
    />);
}


export default DefaultDatePicker;
