package com.inscrum.model.dto

import com.inscrum.model.Task

/**
 * Created by jordi on 06/04/2015.
 */
class TaskDTO(
    override val id: Int,
    override val title: String,
    override val description: String,
    override val estimation: Int,
    override val acceptanceCriteria: String,
    boardColumnId: Int,
    position: Int) extends Task(id, title, description, estimation, acceptanceCriteria){

}
