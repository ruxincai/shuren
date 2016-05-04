import {Component, ElementRef} from 'angular2/core';
import {bootstrap} from 'angular2/platform/browser'
import {Response} from "./http";
import {httpGet} from "./http";
import {extractJSON} from "./http";

@Component({
	selector: 'test-app',
	template: `
	<div class="row">
    <label>Input Text</label>
    <input #input type="text" size="22" [value]="inputText" (input)="setValue(input.value)">
    </div>
    <div *ngIf="msg !== null" class="row error">{{msg}}</div>
	<button class="default" (click)="submit()">Done</button>
	`
})

export class AppComponent {
	inputText: string = '';
	msg: string = 'The input is required';

	constructor(elementRef: ElementRef) {
	}

	setValue(val: string) {
		this.inputText = val;
		this.msg = val === '' ? 'The input is required' : null;
	}

	submit() {
		httpGet('json').then(extractJSON).then(o => this.msg = o);
		/*httpGet('rest/' + this.inputText).then(extractJSON).then(o => {
			this.msg = o;
		});*/
	}
}

bootstrap(AppComponent);