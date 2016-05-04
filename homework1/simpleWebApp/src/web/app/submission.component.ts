import {Component, Input} from 'angular2/core';
import {SubmissionType} from "./main";

@Component({
	selector: 'test-submission',
	template: `
   	<div class="row">
	<span>{{submission.userName}}</span>
	<span>{{submission.city}}</span>
	<span>{{submission.last_update | date}}</span>
	<span class="stretch">{{submission.text}}</span>
	</div>
	<div *ngFor="#r of submission.responses;">{{r}}</div>`
})

export class SubmissionComponent {
	@Input() submission: SubmissionType;

	formatTime(time: number) {
		//let time = this.submission.lastUpdate;
		/*this.formattedTime = time ?
				new Date(time).toLocaleDateString(undefined, DATE_FORMAT) : '';*/
	}
}